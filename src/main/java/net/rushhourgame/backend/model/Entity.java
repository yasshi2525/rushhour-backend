package net.rushhourgame.backend.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.Getter;
import redis.clients.jedis.JedisPooled;

@Getter
abstract class Entity {
  protected final String id;

  @Getter(lazy = true)
  private final double x = toX();
  @Getter(lazy = true)
  private final double y = toY();

  protected Entity(String id) {
    this.id = id;
  }

  public void write(JsonMapper json, JedisPooled jedis) throws JsonProcessingException {
    jedis.jsonSet(id, json.writeValueAsString(this));
  }

  protected abstract double toX();
  protected abstract double toY();
}
