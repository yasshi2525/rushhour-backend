package net.rushhourgame.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import net.rushhourgame.backend.controller.BuildRailwayController;
import net.rushhourgame.backend.handler.HandlerFunctionBuilder;

@Configuration
class AppConfig {
  @Autowired
  private HandlerFunctionBuilder builder;

  @Autowired
  private BuildRailwayController railway;

  @Bean
  RouterFunction<ServerResponse> routes() {
    RequestPredicate rp = RequestPredicates.accept(MediaType.APPLICATION_JSON);

    return RouterFunctions.route()
      .POST("/railway", rp, builder.build(railway))
      .build();
  }
}
