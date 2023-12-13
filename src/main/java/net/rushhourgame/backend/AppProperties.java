package net.rushhourgame.backend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("net.rushhourgame.backend")
public record AppProperties (@NestedConfigurationProperty ModelProperties model){
  public record ModelProperties(double width, double height, double precision) {
  }
}
