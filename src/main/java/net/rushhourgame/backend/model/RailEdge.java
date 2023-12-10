package net.rushhourgame.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class RailEdge {
  @JsonIgnore
  private final Repository repo;

  private final long id;
  private final RailNode from;
  private final RailNode to;

  public RailEdge(Repository repo, long id, RailNode from, RailNode to) {
    this.repo = repo;
    this.id = id;
    this.from = from;
    this.to = to;
    repo.getRailEdges().put(id, this);
  }
}
