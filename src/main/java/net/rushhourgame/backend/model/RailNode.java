package net.rushhourgame.backend.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class RailNode extends PointEntity {
  private final String type = "RailNode";

  @JsonIgnore
  private final Repository repo;

  @JsonIgnore
  private final Map<String, RailEdge> inbound = new HashMap<>();

  @JsonIgnore
  private final Map<String, RailEdge> outbound = new HashMap<>();

  @Getter(lazy = true)
  private final List<ReferedRailEdge> in = toRailEdgeReferences(inbound);

  @Getter(lazy = true)
  private final List<ReferedRailEdge> out = toRailEdgeReferences(outbound);

  public RailNode(Repository repo, String id, Point location) {
    super(id, location);
    this.repo = repo;
    repo.getRailNodes().put(id, this);
  }

  public ReferedRailNode toRef() {
    return new ReferedRailNode(id, location);
  }

  private List<ReferedRailEdge> toRailEdgeReferences(Map<String, RailEdge> map) {
    return map.values().stream().map(RailEdge::toRef).collect(Collectors.toList());
  }
}
