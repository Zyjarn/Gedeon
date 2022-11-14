package com.vten.gedeon.connector.elastic.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = GedeonElasticConnectorProperties.class)
public class GedeonElasticConnectorConfig {

	@Bean(destroyMethod = "close")
	protected RestClient getClient(GedeonElasticConnectorProperties properties) {
		// Security
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));

		RestClientBuilder builder = RestClient
				.builder(new HttpHost(properties.getHost(), properties.getPort(), properties.getProtocol()))
				.setHttpClientConfigCallback(
						httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
		return builder.build();
	}
}
