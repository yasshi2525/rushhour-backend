package net.rushhourgame.backend.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rushhourgame.backend.exception.ApplicationException;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.format.DateTimeFormatter;

import org.slf4j.helpers.MessageFormatter;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BaseResponseBody {
  private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  private static final DateTimeFormatter df = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

  @JsonIgnore
  private final RequestHeader header;

  private final String message;

  @Getter(lazy = true)
  private final String timestamp = toTimeStamp();

  public BaseResponseBody(RequestHeader header, Message type, String... args) {
    this(header, MessageFormatter.arrayFormat(type.getLocalizedMessage(header.getLocale()), args).getMessage());
  }

  public BaseResponseBody(RequestHeader header, ApplicationException e) {
    this(header, e.getLocalMessage(header.getLocale()));
  }

  private String toTimeStamp() {
    return LocalDateTime.now(header.getTimeZone().toZoneId()).format(df);
  }
}
