package net.rushhourgame.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class RailEdge extends EdgeEntity {
  private final String type = "RailEdge";

  @JsonIgnore
  private final Repository repo;

  private final RailNode departure;
  private final RailNode destination;

  public RailEdge(Repository repo, String id, RailNode departure, RailNode destination) {
    super(id, departure, destination);
    this.repo = repo;
    this.departure = departure;
    this.destination = destination;
    this.departure.getOut().put(id, this);
    this.destination.getIn().put(id, this);
    repo.getRailEdges().put(id, this);
  }
}
