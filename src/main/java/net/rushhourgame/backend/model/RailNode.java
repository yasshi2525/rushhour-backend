package net.rushhourgame.backend.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class RailNode {
  @JsonIgnore
  private final Repository repo;

  private final long id;
  private final Point loc;

  @JsonIgnore
  private final Map<Long, RailEdge> in = new HashMap<>();

  @JsonIgnore
  private final Map<Long, RailEdge> out = new HashMap<>();

  public RailNode(Repository repo, long id, Point loc) {
    this.repo = repo;
    this.id = id;
    this.loc = loc;
    repo.getRailNodes().put(id, this);
  }
}
