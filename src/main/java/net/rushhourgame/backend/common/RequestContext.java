package net.rushhourgame.backend.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestContext<T> {
  private final RequestHeader header;
  private final T body;
}
