package net.rushhourgame.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
abstract class EdgeEntity extends Entity {
  @JsonIgnore
  protected PointEntity departure;
  
  @JsonIgnore
  protected PointEntity destination;

  protected EdgeEntity(String id, PointEntity departure, PointEntity destination) {
    super(id);
    this.departure = departure;
    this.destination = destination;
  }
  
  @Override
  protected double toX() {
    return (departure.getX() + destination.getX()) / 2;
  }

  @Override
  protected double toY() {
    return (departure.getX() + destination.getX()) / 2;
  }
}
