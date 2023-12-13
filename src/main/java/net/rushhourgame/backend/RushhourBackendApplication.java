package net.rushhourgame.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("net.rushhourgame.backend")
public class RushhourBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RushhourBackendApplication.class, args);
	}

}
