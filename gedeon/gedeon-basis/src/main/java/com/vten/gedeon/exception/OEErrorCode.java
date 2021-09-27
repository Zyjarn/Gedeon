package com.vten.gedeon.exception;

import lombok.Getter;

public enum OEErrorCode {
	
	/**
	 * OE1001 - Property does not exist in properties definitions list.
	 */
	OE1001("Property does not exist in properties definitions list."),
	/**
	 * OE1002 - Property not in object cache
	 */
	OE1002("Property not in cache"),
	OE3001("Fatal error while parsing 'gedeon.json'. Installer is corrupt."),
	OEXXXX("");
	
	private static final String formatter = "%s - %s";
	
	@Getter
	private String message;
	
	OEErrorCode(String msg) {
		message = msg;
	}
	
	public String toString() {
		return String.format(formatter, name(), getMessage());
	}
}
