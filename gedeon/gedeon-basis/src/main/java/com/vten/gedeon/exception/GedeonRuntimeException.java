package com.vten.gedeon.exception;

import com.vten.gedeon.utils.GedStringUtils;

public class GedeonRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3025519675342066251L;

	public GedeonRuntimeException(Throwable t,String message,Object... additions ) {
		super(String.format(message, additions),t);
	}
	
	public GedeonRuntimeException(String message) {
		super(message);
	}
	
	public GedeonRuntimeException(String message,Throwable t) {
		super(message,t);
	}
	
	public GedeonRuntimeException(GedeonErrorCode error,Throwable t) {
		super(error.toString(),t);
	}
	
	public GedeonRuntimeException(GedeonErrorCode error,String... additions) {
		super(error.toString().concat(String.join(GedStringUtils.DASH_SPACE, additions)));
	}
}
