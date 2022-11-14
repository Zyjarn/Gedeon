package com.vten.pdfgen.model.beans.css.rulestypes;

import com.vten.pdfgen.exception.CSSRuleParseException;

import lombok.Getter;

public class CSSFloatRule extends CSSRuleType {

	@Getter
	private Float value;

	public CSSFloatRule(Float f) {
		super();
		value = f;
	}

	public CSSFloatRule(String s) throws CSSRuleParseException {
		super(s);
	}

	@Override
	public void parse(String stringRepresentation) throws CSSRuleParseException {
		try {
			value = Float.parseFloat(stringRepresentation);
		} catch (Exception e) {
			throw new CSSRuleParseException("Unparsable expected float value : ".concat(stringRepresentation));
		}
	}

	@Override
	public CSSRuleType copy() {
		// TODO Auto-generated method stub
		return new CSSFloatRule(value);
	}

}
