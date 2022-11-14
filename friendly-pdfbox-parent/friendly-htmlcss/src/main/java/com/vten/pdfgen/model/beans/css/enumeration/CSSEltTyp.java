package com.vten.pdfgen.model.beans.css.enumeration;

import lombok.Getter;

/**
 * CSS Element Types
 * 
 * @author Valentin
 *
 */
public enum CSSEltTyp {

	TAG(""), CLASS("."), ID("#");

	@Getter
	private String value;

	CSSEltTyp(String v) {
		value = v;
	}

}
