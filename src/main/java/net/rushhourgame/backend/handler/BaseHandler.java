package net.rushhourgame.backend.handler;

import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;
import net.rushhourgame.backend.common.BaseResponseBody;
import net.rushhourgame.backend.common.Message;
import net.rushhourgame.backend.common.RequestContext;
import net.rushhourgame.backend.common.RequestHeader;
import net.rushhourgame.backend.controller.BaseController;
import net.rushhourgame.backend.exception.ApplicationException;
import net.rushhourgame.backend.exception.BuildingJsonException;
import reactor.core.publisher.Mono;

@Slf4j
public class BaseHandler<T> implements HandlerFunction<ServerResponse> {
  private static final String HEADER_TIMEZONE_KEY = "X-RushHour-TZ";
  private static final String HEADER_TIMESTAMP_KEY = "X-RushHour-Timestamp";
  private static final String REQUEST_HEADER_KEY = "RUSHHOUR_REQUEST_HEADER";

  private final BaseController<T> controller;

  BaseHandler(BaseController<T> controller) {
    this.controller = controller;
  }

  @Override
  public Mono<ServerResponse> handle(ServerRequest request) {
    return Mono.just(request)
      .flatMap(r -> saveHeader(r))
      .flatMap(r -> r.bodyToMono(controller.getRequestEntityClass()))
      .flatMap(body -> createContext(request, body))
      .flatMap(controller.andThen(resEntity -> Mono.just(resEntity)))
      .flatMap(resEntity -> buildJsonString(resEntity))
      .flatMap(resBody -> toServerResponse(ServerResponse.ok(), resBody))
      .doOnError(t -> log.error(t.getMessage(), t))
      .onErrorResume(ApplicationException.class, 
        e -> buildErrorResponseEntity(request, e)
          .flatMap(resEntity -> buildJsonString(resEntity))
          .flatMap(resBody -> toServerResponse(ServerResponse.badRequest(), resBody)))
      .onErrorResume(t -> buildInternalErrorResponseEntity(request, t)
          .flatMap(resEntity -> buildJsonString(resEntity))
          .flatMap(resBody -> toServerResponse(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR), resBody)))
      .doOnError(t -> log.error(t.getMessage(), t));
  }

  private Mono<ServerRequest> saveHeader(ServerRequest request) {
    String tz = request.headers().firstHeader(HEADER_TIMEZONE_KEY);
    request.attributes().put(REQUEST_HEADER_KEY, new RequestHeader(
      Message.lookupAvailableLocale(request.headers().acceptLanguage()),
      tz == null ? TimeZone.getDefault() : TimeZone.getTimeZone(tz),
      request.headers().firstHeader(HEADER_TIMESTAMP_KEY)
    ));
    return Mono.just(request);
  }

  private Mono<RequestContext<T>> createContext(ServerRequest request, T body) {
    return Mono.just(new RequestContext<T>(getHeader(request), body));
  }

  private Mono<ServerResponse> toServerResponse(ServerResponse.BodyBuilder builder, String resBody) {
    return builder.contentType(MediaType.APPLICATION_JSON).bodyValue(resBody);
  }

  private Mono<BaseResponseBody> buildErrorResponseEntity(ServerRequest request, ApplicationException e) {
    return Mono.just(new BaseResponseBody(getHeader(request), e));
  }

  private Mono<BaseResponseBody> buildInternalErrorResponseEntity(ServerRequest request, Throwable t) {
    return Mono.just(new BaseResponseBody(getHeader(request), new ApplicationException(Message.UNKNOWN_ERROR, t)));
  }

  private RequestHeader getHeader(ServerRequest request) {
    return request.exchange().getAttribute(REQUEST_HEADER_KEY);
  }

  private Mono<String> buildJsonString(Object obj) {
    JsonMapper json = new JsonMapper();
    try {
      return Mono.just(json.writeValueAsString(obj));
    } catch (JsonProcessingException e) {
      throw new BuildingJsonException(e);
    } 
  }
}
