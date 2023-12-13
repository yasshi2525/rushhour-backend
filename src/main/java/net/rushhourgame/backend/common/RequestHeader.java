package net.rushhourgame.backend.common;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestHeader {
  private static final String UNKNOWN_REQUEST_TIMESTAMP = "UNKNOWN";
  private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  private static final DateTimeFormatter df = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

  @Nullable
  private final Locale locale;

  private final TimeZone timeZone;

  @Nullable
  @Getter(value = AccessLevel.NONE)
  private final String timestamp;

  @Getter(lazy = true)
  private final String requestDateTime = toDateTime();

  public RequestHeader() {
    this(null, TimeZone.getDefault(), null);
  }

  private String toDateTime() {
    try {
      return df.format(Instant.ofEpochMilli(Long.parseLong(timestamp)));
    } catch (NumberFormatException e) {
      return UNKNOWN_REQUEST_TIMESTAMP;
    }
  }
}
