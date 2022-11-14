package com.vten.pdfgen.model.beans.css.rulestypes;

import org.apache.commons.lang3.StringUtils;

import com.vten.pdfgen.exception.CSSRuleParseException;

import lombok.Getter;

@Getter
public class CSSQuartetRule<T extends CSSRuleType> extends CSSRuleType {

	private T up;
	private T left;
	private T down;
	private T right;

	public CSSQuartetRule(T l, T u, T r, T d) {
		super();
		left = l;
		up = u;
		right = r;
		down = d;
	}

	public CSSQuartetRule(String stringRepresentation) throws CSSRuleParseException {
		super(stringRepresentation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void parse(String value) throws CSSRuleParseException {
		String[] elts = value.split(StringUtils.SPACE);
		Class<? extends CSSRuleType> c = CSSLengthRule.class;
		if (elts.length == 1) {
			left = (T) CSSRuleType.parseRule(c, elts[0]);
			up = (T) CSSRuleType.parseRule(c, elts[0]);
			right = (T) CSSRuleType.parseRule(c, elts[0]);
			down = (T) CSSRuleType.parseRule(c, elts[0]);
		} else if (elts.length == 4) {
			left = (T) CSSRuleType.parseRule(c, elts[0]);
			up = (T) CSSRuleType.parseRule(c, elts[1]);
			right = (T) CSSRuleType.parseRule(c, elts[2]);
			down = (T) CSSRuleType.parseRule(c, elts[3]);
		} else {
			throw new CSSRuleParseException("Unable to parse value :".concat(value));
		}
	}

	@Override
	public CSSRuleType copy() {
		return new CSSQuartetRule<>(left, up, right, down);
	}

}
