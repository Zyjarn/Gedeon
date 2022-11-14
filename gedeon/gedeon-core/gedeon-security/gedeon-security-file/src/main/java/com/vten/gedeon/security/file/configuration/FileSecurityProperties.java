package com.vten.gedeon.security.file.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "security.file")
@Getter
@Setter
@NoArgsConstructor
public class FileSecurityProperties {

	@NonNull
	private String algorithm = "AES/CBC/PKCS5Padding";
	private KeyStore keystore = new KeyStore();

	@Getter
	@Setter
	public class KeyStore {
		@NonNull
		private String type = "JKS";// "PCKS12";
		@NonNull
		private String location = "./gedeonkeys.jks";// ".p12";
		@NonNull
		private String key = "eaRP/QOhaP6o5Ft5XdO+JCuqNL3Kbv3141y+5wUxEr4rwaqg1qfqyivdzhdMZy7DDlsGkdgGh3JQL1GnVH1/PA==";
		// "GedeonIsABadPassword";
	}
}
