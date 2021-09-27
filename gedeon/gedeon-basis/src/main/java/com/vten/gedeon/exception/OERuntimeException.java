package com.vten.gedeon.exception;

public class OERuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3025519675342066251L;

	public OERuntimeException(String message) {
		super(message);
	}
	
	public OERuntimeException(String message,Throwable t) {
		super(message,t);
	}
	
	public OERuntimeException(OEErrorCode error,Throwable t) {
		super(error.toString(),t);
	}
}
