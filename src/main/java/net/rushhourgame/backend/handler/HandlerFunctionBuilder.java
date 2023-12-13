package net.rushhourgame.backend.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import net.rushhourgame.backend.controller.BaseController;

@Component
public class HandlerFunctionBuilder {
  public <T> HandlerFunction<ServerResponse> build(BaseController<T> controller) {
    return new BaseHandler<>(controller);
  }
}
