package com.vten.pdfgen.model.beans.css;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.vten.pdfgen.model.beans.html.HtmlElement;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent a CSS selector, composed of elements with their selectors
 * attributes and css selector on parents elements
 * 
 * @author Valentin
 *
 */
@Getter
@Setter
public class CSSSelector {

	private CSSSelector parentSelector;
	private CSSElement elementSelector;
	private CSSElement idSelector;
	private List<CSSElement> classSelector;

	/**
	 * Get List of class selector elements null safe
	 * 
	 * @return
	 */
	public List<CSSElement> getClassSelector() {
		if (classSelector == null) {
			classSelector = new ArrayList<>();
		}
		return classSelector;
	}

	/**
	 * Check if the given html element match the current selector
	 * 
	 * @param elt html element to check
	 * @return if given htmlElement match the current selector
	 */
	public boolean matchElement(HtmlElement elt) {
		Optional<HtmlElement> parent = elt.getParent();
		// check if match element tag, then id, then match all class and then match
		// parent selector
		return matchElementSelector(elt) && matchIdSelector(elt)
				&& (getClassSelector().stream().allMatch(clsSel -> clsSel.matchElement(elt)))
				&& matchParentSelector(parent);
		// TODO check functions and childrens
	}

	/**
	 * Check if given html element match element selector for current css selector
	 * 
	 * @param elt html element to check by tag name
	 * @return true if element selector match html element or element selector is
	 *         null
	 */
	private boolean matchElementSelector(HtmlElement elt) {
		return elementSelector != null ? elementSelector.matchElement(elt) : true;
	}

	/**
	 * Check if given html element match id selector for current css selector
	 * 
	 * @param elt html element to check by id
	 * @return true if id selector match html element or id selector is null
	 */
	private boolean matchIdSelector(HtmlElement elt) {
		return idSelector != null ? idSelector.matchElement(elt) : true;
	}

	/**
	 * Check if given html element match parent selector for current css selector
	 * 
	 * @param elt optional html element to match with parent css selector
	 * @return true if parent selector match html element or element selector is
	 *         null
	 */
	private boolean matchParentSelector(Optional<HtmlElement> parent) {
		return (parentSelector == null)
				|| ((parent.isPresent() && parentSelector.matchElement(parent.get())) || !parent.isPresent());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (parentSelector != null) {
			sb.append(parentSelector.toString()).append(StringUtils.SPACE);
		}
		if (elementSelector != null) {
			sb.append(elementSelector.toString());
		}
		if (idSelector != null) {
			sb.append(idSelector.toString());
		}
		sb.append(getClassSelector().stream().map(CSSElement::toString).collect(Collectors.joining(",")));

		return sb.toString();
	}
}
