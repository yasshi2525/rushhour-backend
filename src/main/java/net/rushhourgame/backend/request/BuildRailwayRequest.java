package net.rushhourgame.backend.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.rushhourgame.backend.common.BaseRequestBody;
import net.rushhourgame.backend.model.Point;

@Getter
@RequiredArgsConstructor
public class BuildRailwayRequest extends BaseRequestBody {
  @NonNull
  private final Point from;
  @NonNull
  private final Point to;
}
