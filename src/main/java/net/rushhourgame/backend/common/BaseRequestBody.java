package net.rushhourgame.backend.common;

import java.util.Arrays;
import java.util.IllformedLocaleException;
import java.util.Locale;
import java.util.TimeZone;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseRequestBody {
  private String lang;
  private String tz;
  private long tiemstamp;

  @Getter(lazy = true)
  private final RequestContext context = toContext();

  private RequestContext toContext() {
    return new RequestContext(toLocale(), toTimeZone());
  }

  private Locale toLocale() {
    try {
      return new Locale.Builder().setLanguage(lang).build();
    } catch (IllformedLocaleException e) {
      return Locale.getDefault();
    }
  }

  private TimeZone toTimeZone() {
    String id = Arrays.stream(TimeZone.getAvailableIDs())
      .filter(target -> target.equals(tz)).findFirst().orElse(TimeZone.getDefault().getID());
    return TimeZone.getTimeZone(id);
  }
}
