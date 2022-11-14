package com.vten.pdfgen.exception;

import lombok.Getter;

public enum CSSParserError {

	UNKNOW_ERROR("Unexpected issue in parsing process"), INVALID_CHAR("Invalid character"),
	INVALID_ESCAPE("Non escapable character"), INVALID_OPERATOR("Invalid operator"),
	INVALID_ATTRIBUTE_NAME("Invalid attribute name"), INVALID_EOF("Invalid end of file");

	@Getter
	private String message;

	CSSParserError(String msg) {
		message = msg;
	}
}
