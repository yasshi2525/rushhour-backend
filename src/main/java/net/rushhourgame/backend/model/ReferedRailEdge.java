package net.rushhourgame.backend.model;

import lombok.Getter;

@Getter
class ReferedRailEdge extends EdgeEntity {
  private final ReferedRailNode from;
  private final ReferedRailNode to;

  ReferedRailEdge(String id, ReferedRailNode departure, ReferedRailNode destination) {
    super(id, departure, destination);
    this.from = departure;
    this.to = destination;
  }
}
