package net.rushhourgame.backend.model;

import lombok.Getter;

@Getter
class ReferedRailNode extends PointEntity{
  ReferedRailNode(String id, Point location) {
    super(id, location);
  }
}
