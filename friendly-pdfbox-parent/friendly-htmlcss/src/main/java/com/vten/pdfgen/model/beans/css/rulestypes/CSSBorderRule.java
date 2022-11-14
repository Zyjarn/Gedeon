package com.vten.pdfgen.model.beans.css.rulestypes;

import com.vten.pdfgen.exception.CSSRuleParseException;
import com.vten.pdfgen.model.beans.css.enumeration.CSSBorderType;
import com.vten.pdfgen.model.beans.css.enumeration.CSSUnitLength;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CSSBorderRule extends CSSRuleType {

	public static final CSSBorderRule NO_BORDER = new CSSBorderRule(CSSBorderType.NONE, CSSColorRule.WHITE,
			new CSSLengthRule(0F, CSSUnitLength.PX));
	public static final CSSBorderRule DARK_SOLID_BORDER = new CSSBorderRule(CSSBorderType.SOLID, CSSColorRule.BLACK,
			new CSSLengthRule(1F, CSSUnitLength.PX));

	@Getter
	private CSSBorderType type;
	@Getter
	private CSSColorRule color;
	@Getter
	private CSSLengthRule width;

	public CSSBorderRule(String s) throws CSSRuleParseException {
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
		// TODO Auto-generated method stub
		return NO_BORDER;
	}

}
