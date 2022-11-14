package com.vten.gedeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan
public class GedeonRenditionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GedeonRenditionApplication.class, args);
	}

}
