package com.vten.gedeon.webapp.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GedeonWebappConstants {

	public static final String TEMPLATE_URL = "%s://%s:%S";
	public static final String DEFAULT_DOMAIN = "localhost";
	public static final String DEFAULT_PROTOCOL = "http";
	public static final String DEFAULT_PORT = "3000";
}
