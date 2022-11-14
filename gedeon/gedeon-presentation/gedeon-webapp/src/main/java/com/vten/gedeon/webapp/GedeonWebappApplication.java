package com.vten.gedeon.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan
public class GedeonWebappApplication {
	public static void main(String[] args) {
		SpringApplication.run(GedeonWebappApplication.class, args);
	}
}
