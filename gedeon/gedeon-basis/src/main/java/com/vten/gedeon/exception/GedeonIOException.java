package com.vten.gedeon.exception;

import com.vten.gedeon.utils.GedStringUtils;

public class GedeonIOException extends Exception {

	private static final long serialVersionUID = -2025519675342066262L;

	public GedeonIOException(GedeonErrorCode error, Throwable t) {
		super(error.toString(), t);
	}

	public GedeonIOException(GedeonErrorCode error, String... additions) {
		super(error.toString().concat(String.join(GedStringUtils.DASH_SPACE, additions)));
	}

	public GedeonIOException(GedeonErrorCode error, Throwable t, String... additions) {
		super(error.toString().concat(String.join(GedStringUtils.DASH_SPACE, additions)));
	}
}
