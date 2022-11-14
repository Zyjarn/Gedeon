package com.vten.gedeon.exception;

import lombok.Getter;

public enum GedeonErrorCode {

	/**
	 * OE0100 - Error while initializing content encryption module
	 */
	OE0100("Error while initializing content encryption module"),

	/**
	 * OE0101 - Error when initializing content encyptor/decryptor
	 */
	OE0101("Error when initializing content encyptor/decryptor."),

	/**
	 * OE0110 - Content can't be retrieved from path + details
	 */
	OE0110("Content can't be retrieved from path : "),

	/**
	 * OE0111 - Content can't be accessed from given path + details
	 */
	OE0111("Content can't be accessed from path : "),

	/**
	 * OE0113 - Unexpected IO Exception while accessing content
	 */
	OE0113("Unexpected IO Exception while accessing content"),

	/**
	 * OE0114 - Unexpected IO Exception while adding content
	 */
	OE0114("Unexpected IO Exception while adding content"),

	/**
	 * OE0120 - Wrong storage area configuration + details
	 */
	OE0120("Wrong storage area configuration : "),

	/**
	 * OE0140 - Wrong storage area configuration + details
	 */
	OE0140("Unable to access FTP Storage : "),

	/**
	 * OE0141 - Unable to access FTP Storage. Wrong credentials.
	 */
	OE0141("Unable to access FTP Storage. Wrong credentials."),

	/**
	 * OE0142 - Unexpected error while closing ftp connection.
	 */
	OE0142("Unexpected error while closing ftp connection."),

	/**
	 * OE0143 - Unexpected IO error while accessing content from ftp + details
	 */
	OE0143("Unexpected IO error while accessing content from ftp : "),

	/**
	 * OE0144 - Unexpected IO error while adding content to ftp
	 */
	OE0144("Unexpected IO error while accessing adding to ftp"),

	/**
	 * OE1001 - Property does not exist in properties definitions list.
	 */
	OE1001("Property does not exist in properties definitions list."),
	/**
	 * OE1002 - Property missing from cache + details
	 */
	OE1002("Property missing from cache : "),
	/**
	 * OE1003 - Object not found + details
	 */
	OE1003("Object was not found : "),
	/**
	 * OE1003 - Object not found + details
	 */
	OE1004("Parameter can't be null "),

	OE3001("Fatal error while parsing 'gedeon.json'. Installer is corrupt."), OEXXXX("");

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
