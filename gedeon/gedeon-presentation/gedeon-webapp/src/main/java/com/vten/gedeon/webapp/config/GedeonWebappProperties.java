package com.vten.gedeon.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "gedeon.webapp")
@Getter
@Setter
@NoArgsConstructor
public class GedeonWebappProperties {
	
	private String protocol = GedeonWebappConstants.DEFAULT_PROTOCOL;
	private String basename = GedeonWebappConstants.DEFAULT_DOMAIN;
	private String port = GedeonWebappConstants.DEFAULT_PORT;
}
