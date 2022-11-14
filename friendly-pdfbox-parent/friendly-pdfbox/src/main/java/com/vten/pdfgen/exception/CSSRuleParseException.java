package com.vten.pdfgen.exception;

import lombok.Getter;

@Getter
public class CSSRuleParseException extends Exception {

	private static final long serialVersionUID = 8156308606061584146L;

	public CSSRuleParseException(String msg, Throwable t) {
		super(msg, t);
	}

	public CSSRuleParseException(String msg) {
		super(msg);
	}

}
