package net.rushhourgame.backend.exception;

import org.slf4j.helpers.MessageFormatter;

import net.rushhourgame.backend.common.Message;

import java.util.Locale;

public class ApplicationException extends RuntimeException {
  private final Message type;
  private final String[] args;

  public ApplicationException(Message type, String... args) {
    this(type, null, args);
  }

  public ApplicationException(Message type, Throwable cause, String... args) {
    super(cause);
    this.type = type;
    this.args = args.length == 0 ? new String[]{cause.getMessage()} : args;
  }

  @Override
  public String getMessage() {
    return MessageFormatter.arrayFormat(type.getMessage(), args).getMessage();
  }

  public String getLocalMessage(Locale locale) {
    return MessageFormatter.arrayFormat(type.getLocalizedMessage(locale), args).getMessage();
  }
}
