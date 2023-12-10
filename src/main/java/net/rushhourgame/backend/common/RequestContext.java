package net.rushhourgame.backend.common;

import java.util.Locale;
import java.util.TimeZone;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestContext {
  private final Locale locale;
  private final TimeZone timeZone;
}
