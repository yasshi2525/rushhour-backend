package net.rushhourgame.backend.common;

import java.util.ResourceBundle;
import java.util.Locale;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

public enum Message {
  SUCCESS,
  INVALID_REQUEST_BODY_ERROR,
  BUILDING_JSON_ERROR,
  UNKNOWN_ERROR,
  ;

  private static final String BASE_NAME = "net.rushhourgame.backend.common.message";
  private static final ResourceBundle DEFAULT = ResourceBundle.getBundle(BASE_NAME);
  private static final ConcurrentMap<Locale, ResourceBundle> LOCAL = new ConcurrentHashMap<>();

  public String getMessage() {
    return DEFAULT.getString(this.toString());
  }

  public String getLocalMessage(Locale locale) {
    return LOCAL.computeIfAbsent(locale, l -> ResourceBundle.getBundle(BASE_NAME, l)).getString(this.toString());
  }
}
