package com.vten.gedeon.api.utils;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

public class GedId {

	@Getter
	private String value = StringUtils.EMPTY;
	
	/**
	 * Constructor of GedId with String representation
	 * @param id
	 */
	public GedId(String id) {
		value = id;
	}
	
	/**
	 * Check if the current Id is blank
	 * @return result of StringUtils.isBlank on string value of the id
	 */
	public boolean isBlank() {
		return StringUtils.isBlank(value);
	}
	
	/**
	 * Return the string representation of the id
	 * @return
	 */
	@Override
	public String toString() {
		return value;
	}
	
	public static GedId newIdFromValue(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return new GedId(hexString.toString());
	}
}
