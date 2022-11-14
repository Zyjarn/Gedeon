package com.vten.pdfgen.exception;

import lombok.Getter;

@Getter
public class CSSAPIException extends RuntimeException {

	private static final long serialVersionUID = 8156308606061584145L;

	public CSSAPIException(String msg, Throwable t) {
		super(msg, t);
	}

	public CSSAPIException(String msg) {
		super(msg);
	}

}
