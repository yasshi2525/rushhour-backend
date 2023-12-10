package net.rushhourgame.backend.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rushhourgame.backend.exception.ApplicationException;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.netty.util.internal.logging.MessageFormatter;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BaseResponseBody {
  private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  @JsonIgnore
  private final RequestContext context;

  private final String message;

  @Getter(lazy = true)
  private final String timestamp = toTimeStamp();

  public BaseResponseBody(BaseRequestBody req, Message type, String... args) {
    this(req.getContext(), MessageFormatter.arrayFormat(type.getLocalMessage(req.getContext().getLocale()), args).getMessage());
  }

  public BaseResponseBody(RequestContext context, ApplicationException e) {
    this(context, e.getLocalMessage(context.getLocale()));
  }

  private String toTimeStamp() {
    return LocalDateTime.now(context.getTimeZone().toZoneId()).format(df);
  }
}
