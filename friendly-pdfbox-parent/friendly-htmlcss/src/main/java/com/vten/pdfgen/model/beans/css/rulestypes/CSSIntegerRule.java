package com.vten.pdfgen.model.beans.css.rulestypes;

import com.vten.pdfgen.exception.CSSRuleParseException;

import lombok.Getter;

public class CSSIntegerRule extends CSSRuleType {

	@Getter
	private Integer value;

	public CSSIntegerRule(Integer i) {
		super();
		value = i;
	}

	public CSSIntegerRule(String s) throws CSSRuleParseException {
		super(s);
	}

	@Override
	public void parse(String stringRepresentation) throws CSSRuleParseException {
		try {
			value = Integer.parseInt(stringRepresentation);
		} catch (Exception e) {
			throw new CSSRuleParseException("Unparsable expected float value : ".concat(stringRepresentation));
		}
	}

	@Override
	public CSSRuleType copy() {
		// TODO Auto-generated method stub
		return new CSSIntegerRule(value);
	}

}
