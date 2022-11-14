package com.vten.gedeon.connector.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "gedeon")
public class GedeonRestConnectorProperties {

	private String uri;
	private String user;
	private String secret;
}
