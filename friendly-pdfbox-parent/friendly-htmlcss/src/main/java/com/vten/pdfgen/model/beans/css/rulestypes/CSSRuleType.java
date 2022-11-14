package com.vten.pdfgen.model.beans.css.rulestypes;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;

import com.vten.pdfgen.exception.CSSAPIException;
import com.vten.pdfgen.exception.CSSRuleParseException;

public abstract class CSSRuleType {

	protected String stringRepresentation;

	/**
	 * Create a rule type by parsing the given string object
	 * 
	 * @param stringRepresentation string to send to parse function
	 * @throws CSSRuleParseException
	 */
	protected CSSRuleType(String stringRepresentation) throws CSSRuleParseException {
		this.stringRepresentation = stringRepresentation;
		parseLowerNonNull();
	}

	/**
	 * Empty super constructor
	 */
	protected CSSRuleType() {
		// Nothing to do
	}

	/**
	 * Call abstract parse function
	 * 
	 * @throws CSSRuleParseException
	 */
	private void parseLowerNonNull() throws CSSRuleParseException {
		if (StringUtils.isBlank(stringRepresentation)) {
			throw new CSSRuleParseException("Null or blank rule.");
		} else {
			// clean string
			this.stringRepresentation = this.stringRepresentation.trim().toLowerCase();
			// then parse
			parse(stringRepresentation);
		}
	}

	/**
	 * Parse the current object to initialize rule object
	 * 
	 * @param value string to parse
	 */
	public abstract void parse(String value) throws CSSRuleParseException;

	/**
	 * Get a copy of the given object
	 * 
	 * @param source
	 * @return copy of the source object
	 */
	public abstract CSSRuleType copy();

	/**
	 * 
	 * @param clz
	 * @param value
	 * @return
	 * @throws CSSRuleParseException
	 */
	public static CSSRuleType parseRule(Class<? extends CSSRuleType> clz, String value) throws CSSRuleParseException {
		try {
			return clz.getConstructor(String.class).newInstance(value);
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			throw new CSSAPIException("No constructor with string attribute find for rule type.", e);
		}
	}

}
