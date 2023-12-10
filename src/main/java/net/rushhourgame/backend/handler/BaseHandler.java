package net.rushhourgame.backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;
import net.rushhourgame.backend.common.BaseRequestBody;
import net.rushhourgame.backend.common.BaseResponseBody;
import net.rushhourgame.backend.common.Message;
import net.rushhourgame.backend.common.RequestContext;
import net.rushhourgame.backend.controller.BaseController;
import net.rushhourgame.backend.exception.ApplicationException;
import net.rushhourgame.backend.exception.BuildingJsonException;
import reactor.core.publisher.Mono;

@Slf4j
public class BaseHandler<T extends BaseRequestBody> implements HandlerFunction<ServerResponse> {
  private static final String REQUEST_CONTEXT_KEY = "RUSHHOUR_REQUEST_CONTEXT";

  private final BaseController<T> controller;

  BaseHandler(BaseController<T> controller) {
    this.controller = controller;
  }

  @Override
  public Mono<ServerResponse> handle(ServerRequest request) {
    return request.bodyToMono(controller.getRequestEntityClass())
      .flatMap(reqEntity -> saveContext(reqEntity))
      .flatMap(controller.andThen(resEntity -> Mono.just(resEntity)))
      .flatMap(resEntity -> buildJsonString(resEntity))
      .flatMap(resBody -> toServerResponse(ServerResponse.ok(), resBody))
      .doOnError(t -> log.error(t.getMessage(), t))
      .onErrorResume(ApplicationException.class, 
        e -> buildErrorResponseEntity(e)
          .flatMap(resEntity -> buildJsonString(resEntity))
          .flatMap(resBody -> toServerResponse(ServerResponse.badRequest(), resBody)))
      .onErrorResume(t -> buildInternalErrorResponseEntity(t)
          .flatMap(resEntity -> buildJsonString(resEntity))
          .flatMap(resBody -> toServerResponse(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR), resBody)))
      .doOnError(t -> log.error(t.getMessage(), t));
  }

  private Mono<T> saveContext(T reqEntity) {
    log.info("saveContext {}", reqEntity.getContext());
    return Mono.just(reqEntity).contextWrite(ctx -> ctx.put(REQUEST_CONTEXT_KEY, reqEntity.getContext()));
  }

  private Mono<ServerResponse> toServerResponse(ServerResponse.BodyBuilder builder, String resBody) {
    return builder.contentType(MediaType.APPLICATION_JSON).bodyValue(resBody);
  }

  private Mono<BaseResponseBody> buildErrorResponseEntity(ApplicationException e) {
    return getRequestContext().map(context -> new BaseResponseBody(context, e));
  }

  private Mono<BaseResponseBody> buildInternalErrorResponseEntity(Throwable t) {
    return getRequestContext().map(context -> new BaseResponseBody(context, new ApplicationException(Message.UNKNOWN_ERROR, t)));
  }

  private Mono<RequestContext> getRequestContext() {
    Mono.deferContextual(ctx -> {
      log.info(ctx.get(REQUEST_CONTEXT_KEY));
      return Mono.just(ctx); 
     });
    return Mono.deferContextual(ctx -> Mono.just(ctx.getOrDefault(REQUEST_CONTEXT_KEY, new BaseRequestBody().getContext())));
  }

  private Mono<String> buildJsonString(Object obj) {
    log.info("buildJsonString {}", obj);
    JsonMapper json = new JsonMapper();
    try {
      return Mono.just(json.writeValueAsString(obj));
    } catch (JsonProcessingException e) {
      throw new BuildingJsonException(e);
    } 
  }
}
