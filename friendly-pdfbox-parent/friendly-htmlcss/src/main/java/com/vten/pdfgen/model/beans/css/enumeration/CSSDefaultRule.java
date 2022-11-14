package com.vten.pdfgen.model.beans.css.enumeration;

import com.vten.pdfgen.model.beans.css.rulestypes.CSSLengthRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSQuartetRule;

public class CSSDefaultRule {
	public static final CSSQuartetRule<CSSLengthRule> DEFAULT_QUARTET_0PX = new CSSQuartetRule<>(
			new CSSLengthRule(0F, CSSUnitLength.PX), new CSSLengthRule(0F, CSSUnitLength.PX),
			new CSSLengthRule(0F, CSSUnitLength.PX), new CSSLengthRule(0F, CSSUnitLength.PX));
}
