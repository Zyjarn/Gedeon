package com.vten.pdfgen.exception;

/**
 * Exceptions for all issues in pdf rendering
 * 
 * @author Valentin
 *
 */
public class UnsupportedPDFException extends RuntimeException {

	/**
	 * generated uid
	 */
	private static final long serialVersionUID = 4619403799040112610L;

	public UnsupportedPDFException(String msg) {
		super(msg);
	}

	public UnsupportedPDFException(String msg, Throwable t) {
		super(msg, t);
	}
}
