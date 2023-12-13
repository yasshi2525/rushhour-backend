package net.rushhourgame.backend.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;

@Getter
public class RailNode extends PointEntity {
  private final String type = "RailNode";

  @JsonIgnore
  private final Repository repo;

  @JsonIdentityInfo(
    generator = ObjectIdGenerators.StringIdGenerator.class,
    property = "id"
  )
  private final Map<String, RailEdge> in = new HashMap<>();
  
  @JsonIdentityInfo(
    generator = ObjectIdGenerators.StringIdGenerator.class,
    property = "id"
  )
  private final Map<String, RailEdge> out = new HashMap<>();

  public RailNode(Repository repo, String id, Point location) {
    super(id, location);
    this.repo = repo;
    repo.getRailNodes().put(id, this);
  }

}
