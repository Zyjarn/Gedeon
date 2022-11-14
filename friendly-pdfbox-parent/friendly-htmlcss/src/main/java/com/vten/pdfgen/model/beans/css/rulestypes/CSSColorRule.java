package com.vten.pdfgen.model.beans.css.rulestypes;

import java.awt.Color;

import com.vten.pdfgen.exception.CSSRuleParseException;

import lombok.Getter;

public class CSSColorRule extends CSSRuleType {

	public static final CSSColorRule WHITE = new CSSColorRule(Color.WHITE);
	public static final CSSColorRule BLACK = new CSSColorRule(Color.BLACK);
	public static final CSSColorRule TRANSPARENT = new CSSColorRule(new Color(1F, 1F, 1F, 1F));

	@Getter
	private Color value;

	public CSSColorRule(Color c) {
		super();
		value = c;
	}

	public CSSColorRule(String s) throws CSSRuleParseException {
		super(s);
	}

	@Override
	public void parse(String stringRepresentation) throws CSSRuleParseException {
		try {
			// value = Float.parseFloat(stringRepresentation);
		} catch (Exception e) {
			throw new CSSRuleParseException("Unparsable expected float value : ".concat(stringRepresentation));
		}
	}

	@Override
	public CSSRuleType copy() {
		return new CSSColorRule(value);
	}

}
