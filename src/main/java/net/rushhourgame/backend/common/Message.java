package net.rushhourgame.backend.common;

import java.util.ResourceBundle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.MissingResourceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Message {
  SUCCESS,
  INVALID_REQUEST_BODY_ERROR,
  BUILDING_JSON_ERROR,
  UNKNOWN_ERROR,
  ;

  private static final String BASE_NAME = "net.rushhourgame.backend.common.message";
  private static final ConcurrentMap<Locale, ResourceBundle> cache = new ConcurrentHashMap<>();

  public String getMessage() {
    return getLocalizedMessage(Locale.getDefault());
  }

  public String getLocalizedMessage(Locale locale) {
    return lookupResourceBundle(locale).getString(this.toString());
  }

  public static Locale lookupAvailableLocale(List<LanguageRange> acceptables) {
    Locale requirement = Locale.lookup(acceptables, Arrays.asList(Locale.getAvailableLocales()));
    return lookupAvailableLocale(requirement);
  }

  private static ResourceBundle lookupResourceBundle(Locale locale) {
    if (locale == null) {
      locale = Locale.ROOT;
    }
    return cache.computeIfAbsent(locale, l -> ResourceBundle.getBundle(BASE_NAME, lookupAvailableLocale(l)));
  }

  private static Locale lookupAvailableLocale(Locale locale) {
    if (locale == null) {
      locale = Locale.ROOT;
    }
    return ResourceBundle.Control
      .getControl(ResourceBundle.Control.FORMAT_DEFAULT)
      .getCandidateLocales(BASE_NAME, locale)
      .stream().filter(l -> hasResourceBundleOfStrictly(l)).findFirst().get();
  }

  private static boolean hasResourceBundleOfStrictly(Locale locale) {
    try {
      Locale found = ResourceBundle.getBundle(BASE_NAME, locale).getLocale();
      return found.equals(locale);
    } catch (MissingResourceException e) {
      return false;
    }
  }
}
