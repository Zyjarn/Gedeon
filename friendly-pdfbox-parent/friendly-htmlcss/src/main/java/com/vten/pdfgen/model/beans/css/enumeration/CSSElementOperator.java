package com.vten.pdfgen.model.beans.css.enumeration;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * CSS Attribute Operator operators for a css attribute selector
 * 
 * @author Valentin
 *
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum CSSElementOperator {

	INSIDE(""), FIRST("+"), PARENT(">"), PREVIOUS("~");

	private String operator;

	/**
	 * Get an instance of CSSAttrOp from the given value
	 * 
	 * @param value
	 * @return CSSAttrOp enum that match the value or NONE by default
	 */
	public static CSSElementOperator fromString(String value) {
		String val = value.trim();
		for (CSSElementOperator attrOp : CSSElementOperator.values()) {
			if (StringUtils.equals(attrOp.operator, val)) {
				return attrOp;
			}
		}
		throw new IllegalArgumentException(String.format("Invalid operator %s", value));
	}

	@Override
	public String toString() {
		return operator;
	}
}
