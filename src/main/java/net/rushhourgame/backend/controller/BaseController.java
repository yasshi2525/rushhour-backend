package net.rushhourgame.backend.controller;

import java.util.function.Function;

import org.springframework.core.ResolvableType;

import net.rushhourgame.backend.common.BaseRequestBody;
import net.rushhourgame.backend.common.BaseResponseBody;

public interface BaseController<T extends BaseRequestBody> extends Function<T, BaseResponseBody> {

  @SuppressWarnings("unchecked")
  public default Class<T> getRequestEntityClass() {
    return (Class<T>) ResolvableType.forClass(getClass()).getInterfaces()[0].resolveGeneric(0);
  }
}
