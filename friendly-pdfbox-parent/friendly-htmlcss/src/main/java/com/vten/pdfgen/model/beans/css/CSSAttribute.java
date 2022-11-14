package com.vten.pdfgen.model.beans.css;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.vten.pdfgen.model.beans.css.enumeration.CSSAttrOp;
import com.vten.pdfgen.model.beans.html.HtmlAttribute;
import com.vten.pdfgen.model.beans.html.HtmlElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent an attribute in a css rule. As "a[name$=.pdf]", the object would
 * represent the part beetween []
 * 
 * @author Valentin
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CSSAttribute {
	private static final String TOSTR1 = "[%s]";
	private static final String TOSTR2 = "[%s%s%s]";

	private String key;
	private String value;
	private CSSAttrOp operator;

	/**
	 * 
	 * @param k attribute name
	 */
	public CSSAttribute(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		if (value == null) {
			return String.format(TOSTR1, key);
		} else {
			return String.format(TOSTR2, key, operator, value);
		}
	}

	/**
	 * Check if given html attribute match given html element
	 * 
	 * @param elt
	 * @return true if html element match the current attribute
	 */
	public boolean matchElement(HtmlElement elt) {
		// Check if html contains attribute
		Optional<HtmlAttribute> attr = elt.getAttribute(key);
		if (!attr.isPresent()) {
			return false;
		}
		// then check if match operator and value
		switch (operator) {
		case BEGIN_WITH:
			return StringUtils.startsWith(attr.get().getValue(), value);
		case CONTAINS:
			return StringUtils.containsIgnoreCase(attr.get().getValue(), value);
		case CONTAINS_WORD:
			return StringUtils.containsAny(value, attr.get().getValue().split(StringUtils.SPACE));
		case END_WITH:
			return StringUtils.endsWith(attr.get().getValue(), value);
		case EQUALS:
			return StringUtils.equals(attr.get().getValue(), value);
		case EQUAL_OR_START:
			return StringUtils.startsWith(attr.get().getValue(), value)
					|| StringUtils.equals(attr.get().getValue(), value);
		case NONE:
			// Attribute exist
			return true;
		default:
			return false;
		}
	}
}
