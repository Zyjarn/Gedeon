package com.vten.pdfgen.model.beans.css;

import java.util.HashMap;
import java.util.Map;

import com.vten.pdfgen.model.beans.css.enumeration.CSSEltTyp;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontWeight;
import com.vten.pdfgen.model.beans.html.HtmlElement;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CSSStyleSheet {

	/**
	 * Map that contains CSSRules<br>
	 * Initialized with default css value
	 */
	private Map<CSSSelector, CSSRules> stylesheet = getDefault();

	/**
	 * Add a new css rule to the current style map
	 * 
	 * @param selector css selector
	 * @param CSSRules style to apply
	 */
	public void addSelectorRule(CSSSelector selector, CSSRules rules) {
		stylesheet.put(selector, rules);
	}

	/**
	 * retrieve the rule associated to the given element
	 * 
	 * @param obj
	 * @return
	 */
	public CSSRules getStyle(HtmlElement obj) {
		CSSRules rules = new CSSRules();

		for (Map.Entry<CSSSelector, CSSRules> entryRule : stylesheet.entrySet()) {
			if (entryRule.getKey().matchElement(obj)) {
				// TODO compute multiple result and inline style
				rules = entryRule.getValue();
			}
		}

		return rules;
	}

	private static Map<CSSSelector, CSSRules> getDefault() {
		Map<CSSSelector, CSSRules> defaultStyle = new HashMap<>();

		CSSSelector h1selector = new CSSSelector();
		CSSElement h1 = new CSSElement();
		h1.setName("h1");
		h1.setType(CSSEltTyp.TAG);
		h1selector.setElementSelector(h1);

		CSSRules h1rules = new CSSRules();
		h1rules.setFontSize("32px");
		h1rules.setFontWeight(CSSFontWeight.BOLD);
		defaultStyle.put(h1selector, h1rules);

		CSSSelector h2selector = new CSSSelector();
		CSSElement h2 = new CSSElement();
		h2.setName("h2");
		h2.setType(CSSEltTyp.TAG);
		h2selector.setElementSelector(h2);

		CSSRules h2rules = new CSSRules();
		h2rules.setFontSize("30px");
		h2rules.setFontWeight(CSSFontWeight.BOLD);
		defaultStyle.put(h2selector, h2rules);

		return defaultStyle;
	}
}
