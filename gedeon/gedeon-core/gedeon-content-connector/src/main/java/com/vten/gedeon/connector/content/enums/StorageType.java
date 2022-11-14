package com.vten.gedeon.connector.content.enums;

import org.apache.commons.lang3.StringUtils;

public enum StorageType {

	FILESYSTEM, FTP;

	/**
	 * Parse given string to storage type, case unsentive
	 * 
	 * @param str not sensitive case string representation of storage type
	 * @return StorageType instance if found or null
	 */
	public static StorageType parse(String str) {
		for (StorageType st : values()) {
			if (StringUtils.equalsIgnoreCase(str, st.name())) {
				return st;
			}
		}
		return null;
	}
}
