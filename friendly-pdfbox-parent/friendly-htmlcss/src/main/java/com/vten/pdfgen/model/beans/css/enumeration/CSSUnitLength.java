package com.vten.pdfgen.model.beans.css.enumeration;

import org.apache.commons.lang3.StringUtils;

import com.vten.pdfgen.exception.CSSRuleParseException;

import lombok.Getter;

public enum CSSUnitLength {
	PX, EM, CM, MM, IN, PT, PC, REM, PERCENT("%");

	@Getter
	private String value;

	CSSUnitLength() {
		value = this.name().toLowerCase();
	}

	CSSUnitLength(String val) {
		value = val;
	}

	/**
	 * Get an instance from given string
	 * 
	 * @param value
	 * @return corresponding enum instance
	 * @throws CSSRuleParseException if no type match
	 */
	public static CSSUnitLength fromString(String value) throws CSSRuleParseException {
		String vLow = StringUtils.toRootLowerCase(value);
		for (CSSUnitLength o : CSSUnitLength.values()) {
			if (StringUtils.equalsIgnoreCase(o.getValue(), vLow)) {
				return o;
			}
		}
		throw new CSSRuleParseException("Unexpected unit type : ".concat(value));
	}

	@Override
	public String toString() {
		return value;

	}
}
