package net.rushhourgame.backend.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.NoArgsConstructor;

@RequestScope
@NoArgsConstructor
@Getter
public class Repository {
  private final Map<String, RailNode> railNodes = new HashMap<>();
  private final Map<String, RailEdge> railEdges = new HashMap<>();
}
