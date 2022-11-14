package com.vten.pdfgen.model.beans.css.rulestypes;

import com.vten.pdfgen.exception.CSSRuleParseException;
import com.vten.pdfgen.model.beans.css.enumeration.CSSUnitLength;

import lombok.Getter;

/**
 * CSSRule object value for "legnth" rules like : <br/>
 * "15px" or "0.8em"
 *
 */
@Getter
public class CSSLengthRule extends CSSRuleType {

	private static final String FORMAT = "%s %s";

	private Float value;
	private CSSUnitLength unit;

	public CSSLengthRule(String stringRepresentation) throws CSSRuleParseException {
		super(stringRepresentation);
	}

	public CSSLengthRule(Float val, CSSUnitLength cssUnit) {
		super();
		value = val;
		unit = cssUnit;
	}

	@Override
	public void parse(String val) throws CSSRuleParseException {
		// TODO check how to handle auto/inherit etc ?
		StringBuilder strValue = new StringBuilder();
		StringBuilder strUnit = new StringBuilder();
		boolean isUnit = true;
		char c;
		for (int i = 0; i < val.length(); i++) {
			c = val.charAt(i);
			if (isUnit && isValue(c)) {
				strValue.append(c);
			} else if (Character.isAlphabetic(c)) {
				isUnit = false;
				strUnit.append(c);
			}
		}
		try {
			value = Float.parseFloat(strValue.toString());
			unit = CSSUnitLength.fromString(strUnit.toString());
		} catch (Exception e) {
			throw new CSSRuleParseException("Unparsable rule : ".concat(val), e);
		}
	}

	private boolean isValue(char c) {
		return Character.isDigit(c) || (c == '.') || (c == ' ');
	}

	@Override
	public CSSRuleType copy() {
		return new CSSLengthRule(value, unit);
	}

	@Override
	public String toString() {
		return String.format(FORMAT, value, unit);
	}

}
