package com.vten.gedeon.connector.elastic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "elasticsearch")
@Getter
@Setter
@NoArgsConstructor
public class GedeonElasticConnectorProperties {

	private String host;

	private int port;

	private String protocol;
}
