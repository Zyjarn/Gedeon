package com.vten.gedeon.webapp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(GedeonWebappProperties.class)
public class GedeonWebappConfig {
	
	/**
	 * Enable cross origin
	 * @param properties
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer(GedeonWebappProperties properties) {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String url = String.format(GedeonWebappConstants.TEMPLATE_URL, properties.getProtocol(),
						properties.getBasename(),properties.getPort());
				registry.addMapping("/api/test").allowedOrigins(url);
			}
		};
	}
}
