package com.vten.pdfgen.model.beans.css;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vten.pdfgen.model.beans.css.enumeration.CSSProperty;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSBorderRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSColorRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSFloatRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSFontRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSIntegerRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSLengthRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSQuartetRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSRuleType;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontStyle;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontWeight;

public class CSSRules extends ArrayList<CSSRule> {

	private static final long serialVersionUID = -2995713385228945079L;

	private static final String SEMICOLON = ";";
	private static final String FORMAT = "{%s}";

	public <T extends CSSRuleType> T getRuleValue(CSSProperty property) {
		return getRuleValue(property, false);
	}

	@SuppressWarnings("unchecked")
	public <T extends CSSRuleType> T getRuleValue(CSSProperty property, boolean saveDefault) {
		Optional<CSSRule> colspanRule = stream().filter(r -> property.equals(r.getProperty())).findFirst();
		// TODO in case of font or others, must add default value to rule
		if (colspanRule.isPresent() && colspanRule.get().isValid()) {
			return (T) colspanRule.get().getValue();
		} else {
			// Return a copy of the default value to not alter it
			CSSRuleType rule = (CSSRuleType) property.getDefaultValue();
			CSSRuleType newRule = rule.copy();
			if (saveDefault) {
				// TODO may be useless if stylesheet initialized with default value
				add(new CSSRule(property, newRule));
			}

			return (T) newRule;
		}
	}

	public CSSIntegerRule getColspan() {
		return getRuleValue(CSSProperty.COLSPAN);
	}

	public CSSLengthRule getFontSize() {
		return getFont().getFontSize();
	}

	public void setFontSize(String size) {
		getFont().setFontSize((CSSLengthRule) new CSSRule(CSSProperty.FONTSIZE.getName(), size).getValue());
	}

	public CSSFontStyle getFontStyle() {
		return getFont().getFontStyle();
	}

	public void setFontStyle(CSSFontStyle fontStyle) {
		getFont().setFontStyle(fontStyle);
	}

	public CSSFontWeight getFontWeight() {
		return getFont().getFontWeight();
	}

	public void setFontWeight(CSSFontWeight fontWeight) {
		getFont().setFontWeight(fontWeight);
	}

	public CSSQuartetRule<CSSLengthRule> getMargin() {
		return getRuleValue(CSSProperty.MARGIN);
	}

	public void setMargin(String margin) {
		add(new CSSRule(CSSProperty.MARGIN.getName(), margin));
	}

	public CSSQuartetRule<CSSLengthRule> getPadding() {
		return getRuleValue(CSSProperty.PADDING);
	}

	public CSSFontRule getFont() {
		return getRuleValue(CSSProperty.FONT, true);
	}

	public CSSFloatRule getOpacity() {
		return getRuleValue(CSSProperty.OPACITY);
	}

	public CSSColorRule getBackgroundColor() {
		return getRuleValue(CSSProperty.BACKGROUNGCOLOR);
	}

	public CSSBorderRule getBorder() {
		return getRuleValue(CSSProperty.BORDER);
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * private Quartet margin = Quartet.DEFAULT; private Quartet padding =
	 * Quartet.DEFAULT; private Color color = Color.BLACK; private Color bgColor =
	 * TRANSPARENT; private int colSpan = 1; private int rowSpan = 1; private
	 * FontCollection font = new FontCollection(); private int fontSize = 12;
	 * private float opacity = 1; private boolean bold = false; private boolean
	 * italic = false; private boolean underline = false; private Justify justify =
	 * Justify.NO; private Border border = Border.NOBORDER;
	 */

	@Override
	public String toString() {
		return String.format(FORMAT, stream().map(CSSRule::toString).collect(Collectors.joining(SEMICOLON)));
	}
}
