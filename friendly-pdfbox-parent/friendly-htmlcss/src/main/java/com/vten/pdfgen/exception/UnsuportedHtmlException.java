package com.vten.pdfgen.exception;

/**
 * Exception for HtmlToPdfService
 * 
 * @author Valentin
 *
 */
public class UnsuportedHtmlException extends Exception {

	/**
	 * generated uid
	 */
	private static final long serialVersionUID = 2932948871745381668L;

	public UnsuportedHtmlException(String message, Throwable t) {
		super(message, t);
	}

	public UnsuportedHtmlException(String message) {
		super(message);
	}
}
