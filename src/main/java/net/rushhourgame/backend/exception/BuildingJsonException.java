package net.rushhourgame.backend.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.rushhourgame.backend.common.Message;

public class BuildingJsonException extends ApplicationException {
  public BuildingJsonException(JsonProcessingException e) {
    super(Message.BUILDING_JSON_ERROR, e);
  }
}
