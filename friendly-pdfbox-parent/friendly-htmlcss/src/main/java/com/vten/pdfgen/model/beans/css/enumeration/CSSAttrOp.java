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
public enum CSSAttrOp {

	NONE(""), EQUALS("="), CONTAINS("*="), EQUAL_OR_START("|="), BEGIN_WITH("^="), END_WITH("$="), CONTAINS_WORD("~=");

	private String operator;

	/**
	 * Get an instance of CSSAttrOp from the given value
	 * 
	 * @param value
	 * @return CSSAttrOp enum that match the value or NONE by default
	 */
	public static CSSAttrOp fromString(String value) {
		for (CSSAttrOp attrOp : CSSAttrOp.values()) {
			if (StringUtils.equalsIgnoreCase(attrOp.operator, value)) {
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
