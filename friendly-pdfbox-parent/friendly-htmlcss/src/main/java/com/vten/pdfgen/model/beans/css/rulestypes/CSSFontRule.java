package com.vten.pdfgen.model.beans.css.rulestypes;

import com.vten.pdfgen.exception.CSSRuleParseException;
import com.vten.pdfgen.model.beans.css.enumeration.CSSUnitLength;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontFamily;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontStyle;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontWeight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CSSFontRule extends CSSRuleType {

	@Getter
	@Setter
	private CSSLengthRule fontSize = new CSSLengthRule(16F, CSSUnitLength.PX);

	@Getter
	@Setter
	private CSSFontWeight fontWeight = CSSFontWeight.NORMAL;

	@Getter
	@Setter
	private CSSLengthRule lineHeight = new CSSLengthRule(18F, CSSUnitLength.PX);

	@Getter
	@Setter
	private CSSFontStyle fontStyle = CSSFontStyle.NORMAL;

	@Getter
	@Setter
	private CSSFontFamily fontFamily = CSSFontFamily.DEFAULT;

	public CSSFontRule(String s) throws CSSRuleParseException {
		super(s);
	}

	public CSSFontRule() {
		super();
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
		return new CSSFontRule();
	}

}
