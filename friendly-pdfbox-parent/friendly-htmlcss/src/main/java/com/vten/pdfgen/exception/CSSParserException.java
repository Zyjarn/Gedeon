package com.vten.pdfgen.exception;

import lombok.Getter;

@Getter
public class CSSParserException extends Exception {

	private static final long serialVersionUID = -2505985659588904300L;

	private static final String FORMAT_ERROR_MSG = "%s at line=%d, pos=%d, char=%s";
	private static final String FORMAT_ERROR_MSG_STATUS = "%s at line=%d, pos=%d, status=%d";

	public CSSParserException(CSSParserError error, int lNb, int cNb, char c, Throwable t) {
		super(String.format(FORMAT_ERROR_MSG, error.getMessage(), lNb, cNb + 1, c), t);
	}

	public CSSParserException(CSSParserError error, int lNb, int cNb, char c) {
		super(String.format(FORMAT_ERROR_MSG, error.getMessage(), lNb, cNb + 1, c));
	}

	public CSSParserException(CSSParserError error, int lNb, int cNb, int status) {
		super(String.format(FORMAT_ERROR_MSG_STATUS, error.getMessage(), lNb, cNb + 1, status));
	}
}
