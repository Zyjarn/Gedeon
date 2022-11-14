package com.vten.pdfgen.model.beans.css;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.vten.pdfgen.model.beans.css.enumeration.CSSEltTyp;
import com.vten.pdfgen.model.beans.html.HtmlElement;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent an element in a css rules. An element can be a tag, an id or a
 * class.
 * 
 * @author Valentin
 *
 */
@Getter
@Setter
public class CSSElement {

	private static final String REGEX_ATTR = "\\[[\\w\\*~$^\\|=-]*\\]";
	private static final Pattern PATTERN_ATTR = Pattern.compile(REGEX_ATTR);

	private CSSEltTyp type;
	private String name;
	private List<CSSAttribute> attributes = new ArrayList<>();

	/**
	 * Get a CSSElement instance from given string
	 * 
	 * @param str string to parse
	 * @return new instance
	 */
	public static CSSElement parse(String str) {
		if (StringUtils.isBlank(str)) {
			throw new IllegalArgumentException();
		}
		CSSElement elt = new CSSElement();

		int startIndex = 1;
		// Determine type from first char
		if (str.startsWith(CSSEltTyp.ID.getValue())) {
			elt.setType(CSSEltTyp.ID);
		} else if (str.startsWith(CSSEltTyp.CLASS.getValue())) {
			elt.setType(CSSEltTyp.CLASS);
		} else {
			// TODO check tag name ?
			elt.setType(CSSEltTyp.TAG);
			startIndex = 0;
		}
		// If attribute exists, set endIndex to it's position
		int indexAttribute = str.indexOf('[');
		int endIndex = indexAttribute > 0 ? indexAttribute : str.length();

		elt.setName(str.substring(startIndex, endIndex));
		// Parse attributes individually
		/*
		 * elt.setAttributes(PATTERN_ATTR.matcher(str).results().map(MatchResult::group)
		 * .map(CSSAttribute::parse) .collect(Collectors.toList()));
		 */

		return elt;
	}

	@Override
	public String toString() {
		return type.getValue().concat(name)
				.concat(attributes.stream().map(CSSAttribute::toString).collect(Collectors.joining()));
	}

	/**
	 * Check if given CSS selector element match given html element
	 * 
	 * @param elt
	 * @return true if elt match css selector element name depending of type and all
	 *         attributes match too
	 */
	public boolean matchElement(HtmlElement elt) {
		return matchName(elt) && attributes.stream().allMatch(attr -> attr.matchElement(elt));
	}

	/**
	 * Check if current name match given html element depending of css element type
	 * <br>
	 * class : check if any html class of html element match name <br>
	 * id : check if id of html element match name <br>
	 * tag : check if tag type of html element match name
	 * 
	 * @param elt
	 * @return
	 */
	private boolean matchName(HtmlElement elt) {
		switch (type) {
		case CLASS:
			return elt.getHtmlClass().stream().anyMatch(cls -> StringUtils.equalsIgnoreCase(name, cls));
		case ID:
			return StringUtils.equalsIgnoreCase(name, elt.getId());
		case TAG:
			return StringUtils.equalsIgnoreCase(name, elt.getTagType().name());
		default:
			return false;
		}
	}

}
