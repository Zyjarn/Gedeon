package com.vten.gedeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan
public class GedeonWSApplication {

	public static void main(String[] args) {
		SpringApplication.run(GedeonWSApplication.class, args);
	}

}
