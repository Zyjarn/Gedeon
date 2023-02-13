package com.vten.gedeon.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
public class GedeonWebappApplication {
	public static void main(String[] args) {
		SpringApplication.run(GedeonWebappApplication.class, args);
	}
	
}
