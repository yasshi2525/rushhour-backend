package net.rushhourgame.backend.controller;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import net.rushhourgame.backend.common.BaseResponseBody;
import net.rushhourgame.backend.common.Message;
import net.rushhourgame.backend.common.RequestContext;
import net.rushhourgame.backend.exception.BuildingJsonException;
import net.rushhourgame.backend.model.RailEdge;
import net.rushhourgame.backend.model.RailNode;
import net.rushhourgame.backend.model.Repository;
import net.rushhourgame.backend.request.BuildRailwayRequest;

import redis.clients.jedis.JedisPooled;

@Component
public class BuildRailwayController implements BaseController<BuildRailwayRequest> {
  @Override
  public BaseResponseBody apply(RequestContext<BuildRailwayRequest> context) {
    BuildRailwayRequest body = context.getBody();
    JsonMapper json = new JsonMapper();
    try (JedisPooled jedis = new JedisPooled()) {
      Repository repo = new Repository();
      RailNode from = new RailNode(repo, UUID.randomUUID().toString(), body.getFrom());
      RailNode to = new RailNode(repo, UUID.randomUUID().toString(), body.getTo());
      RailEdge edge = new RailEdge(repo, UUID.randomUUID().toString(), from, to);
      from.write(json, jedis);
      to.write(json, jedis);
      edge.write(json, jedis);
    } catch (JsonProcessingException e) {
      throw new BuildingJsonException(e);
    }
    return new BaseResponseBody(context.getHeader(), Message.SUCCESS);
  }
}
