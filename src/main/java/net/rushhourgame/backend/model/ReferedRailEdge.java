package net.rushhourgame.backend.model;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
class ReferedRailEdge extends EdgeEntity {
  @Getter(AccessLevel.PUBLIC)
  private final ReferedRailNode from;
  @Getter(AccessLevel.PUBLIC)
  private final ReferedRailNode to;

  ReferedRailEdge(String id, ReferedRailNode departure, ReferedRailNode destination) {
    super(id, departure, destination);
    this.from = departure;
    this.to = destination;
  }
}
