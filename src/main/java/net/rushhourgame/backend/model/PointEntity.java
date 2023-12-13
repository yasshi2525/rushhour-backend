package net.rushhourgame.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
abstract class PointEntity extends Entity {
  @JsonIgnore
  protected final Point location;

  protected PointEntity(String id, Point location) {
    super(id);
    this.location = location;
  }

  protected double toX() {
    return location.getX();
  }

  protected double toY() {
    return location.getY();
  }
}
