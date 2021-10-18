package com.vten.gedeon.exception;

public class GedeonJsonException extends GedeonRuntimeException {
	
	private static final long serialVersionUID = 3857848847482276999L;

	public GedeonJsonException(String message) {
		super(message);
	}
	
	public GedeonJsonException(String message,Throwable t) {
		super(message,t);
	}
	
}
