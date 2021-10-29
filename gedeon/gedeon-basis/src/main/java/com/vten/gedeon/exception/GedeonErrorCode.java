package com.vten.gedeon.exception;

import lombok.Getter;

public enum GedeonErrorCode {
	
	/**
	 * OE1001 - Property does not exist in properties definitions list.
	 */
	OE1001("Property does not exist in properties definitions list."),
	/**
	 * OE1002 - Property not in object cache
	 */
	OE1002("Property not in cache : "),
	/**
	 * OE1003 - Object not found
	 */
	OE1003("Object was not found : "),
	/**
	 * OE1003 - Object not found
	 */
	OE1004("Parameter can't be null "),
	
	OE3001("Fatal error while parsing 'gedeon.json'. Installer is corrupt."),
	OEXXXX("");
	
	private static final String FORMATER = "%s - %s";
	
	@Getter
	private String message;
	
	GedeonErrorCode(String msg) {
		message = msg;
	}
	
	@Override
	public String toString() {
		return String.format(FORMATER, name(), getMessage());
	}
}
