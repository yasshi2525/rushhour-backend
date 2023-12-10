package net.rushhourgame.backend.controller;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;
import net.rushhourgame.backend.common.BaseResponseBody;
import net.rushhourgame.backend.common.Message;
import net.rushhourgame.backend.exception.BuildingJsonException;
import net.rushhourgame.backend.model.RailEdge;
import net.rushhourgame.backend.model.RailNode;
import net.rushhourgame.backend.model.Repository;
import net.rushhourgame.backend.request.BuildRailwayRequest;
import redis.clients.jedis.JedisPooled;

@Slf4j
@Component
public class BuildRailwayController implements BaseController<BuildRailwayRequest> {
  @Override
  public BaseResponseBody apply(BuildRailwayRequest v) {
    JsonMapper json = new JsonMapper();
    try (JedisPooled jedis = new JedisPooled()) {
      Repository repo = new Repository();
      RailNode from = new RailNode(repo, 1, v.getFrom());
      RailNode to = new RailNode(repo, 2, v.getTo());
      RailEdge edge = new RailEdge(repo, 1, from, to);
      jedis.jsonSet("rn" + from.getId(), json.writeValueAsString(from));
      jedis.jsonSet("rn" + to.getId(), json.writeValueAsString(to));
      jedis.jsonSet("re" + edge.getId(), json.writeValueAsString(edge));
      log.info("wrote rn1-re1-rn2");
    } catch (JsonProcessingException e) {
      throw new BuildingJsonException(e);
    }
    return new BaseResponseBody(v, Message.SUCCESS);
  }
}
