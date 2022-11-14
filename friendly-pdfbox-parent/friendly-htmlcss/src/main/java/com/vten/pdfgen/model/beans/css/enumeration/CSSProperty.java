package com.vten.pdfgen.model.beans.css.enumeration;

import com.vten.pdfgen.model.beans.css.rulestypes.CSSBorderRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSColorRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSFloatRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSFontRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSIntegerRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSLengthRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSQuartetRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSRuleType;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontFamily;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontStyle;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontWeight;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CSSProperty {

	BACKGROUNGCOLOR("padding", CSSColorRule.class, CSSColorRule.TRANSPARENT),
	BORDER("border", CSSBorderRule.class, CSSBorderRule.NO_BORDER),
	COLSPAN("colspan", CSSIntegerRule.class, new CSSIntegerRule(1)),
	FONT("font", CSSFontRule.class,
			new CSSFontRule(new CSSLengthRule(16F, CSSUnitLength.PX), CSSFontWeight.NORMAL,
					new CSSLengthRule(18F, CSSUnitLength.PX), CSSFontStyle.NORMAL, CSSFontFamily.DEFAULT)),
	FONTSIZE("font-size", CSSLengthRule.class, new CSSLengthRule(16F, CSSUnitLength.PX)),
	MARGIN("margin", CSSQuartetRule.class, CSSDefaultRule.DEFAULT_QUARTET_0PX),
	OPACITY("opacity", CSSFloatRule.class, new CSSFloatRule(1F)),
	PADDING("padding", CSSQuartetRule.class, CSSDefaultRule.DEFAULT_QUARTET_0PX), UNKNOWN("", CSSRuleType.class, null);

	private String name;
	private Class<? extends CSSRuleType> propertyType;
	private Object defaultValue;

	public static CSSProperty fromString(String str) {
		for (CSSProperty value : CSSProperty.values()) {
			if (value.getName().equalsIgnoreCase(str)) {
				return value;
			}
		}
		return UNKNOWN;
	}
}
