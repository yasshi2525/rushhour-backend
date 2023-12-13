package net.rushhourgame.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class RailEdge extends EdgeEntity {
  private final String type = "RailEdge";

  @JsonIgnore
  private final Repository repo;

  @JsonIgnore
  private final RailNode departure;

  @JsonIgnore
  private final RailNode destination;

  private final ReferedRailNode from;
  private final ReferedRailNode to;

  public RailEdge(Repository repo, String id, RailNode departure, RailNode destination) {
    super(id, departure, destination);
    this.repo = repo;
    this.departure = departure;
    this.destination = destination;
    this.departure.getOutbound().put(id, this);
    this.destination.getInbound().put(id, this);
    this.from = departure.toRef();
    this.to = destination.toRef();
    repo.getRailEdges().put(id, this);
  }

  public ReferedRailEdge toRef() {
    return new ReferedRailEdge(id, departure.toRef(), destination.toRef());
  }
}
