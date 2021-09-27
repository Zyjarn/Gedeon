package com.vten.gedeon.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vten.gedeon"})
@EnableAutoConfiguration
public class GedeonWSApplication {

	public static void main(String[] args) {
		SpringApplication.run(GedeonWSApplication.class, args);
	}

}
