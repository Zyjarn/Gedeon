package com.vten.pdfgen.model.beans.css;

import com.vten.pdfgen.exception.CSSRuleParseException;
import com.vten.pdfgen.model.beans.css.enumeration.CSSProperty;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSRuleType;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSSRule {

	private static final String FORMAT = "%s : %s";

	@Getter
	@NonNull
	private String propertyName;
	@Getter
	private CSSProperty property;

	@Getter
	private String stringValueRepresentation;

	@Getter
	private CSSRuleType value;
	@Getter
	private boolean valid;

	public CSSRule(CSSProperty property, CSSRuleType ruleValue) {
		if (!property.getPropertyType().equals(ruleValue.getClass())) {
			throw new IllegalArgumentException();
		}
		this.property = property;
		this.propertyName = this.property.getName();
		this.value = ruleValue;
		this.stringValueRepresentation = this.value.toString();
		this.valid = true;
	}

	/**
	 * Create an instance of CSSRule, and check if the rule is valid regarding the
	 * CSS3 specifications.<br/>
	 * Rule is invalid if property name is not an existing css property<br/>
	 * And value must be of correct type for property.
	 * 
	 * @param propertyName css property, transformed to lower case and can't be null
	 * @param ruleValue    string representation of the rule
	 */
	public CSSRule(@NonNull String propertyName, @NonNull String ruleValue) {
		valid = true;
		this.property = CSSProperty.fromString(propertyName);
		// Set property name
		if (CSSProperty.UNKNOWN.equals(this.property)) {
			// if not a valid property name
			this.propertyName = propertyName.toLowerCase();
			valid = false;
		} else {
			this.propertyName = this.property.getName();
		}
		setStringValueRepresentation(ruleValue);
	}

	/**
	 * replace current value with parsed result of given parameter
	 * 
	 * @param ruleValue
	 */
	public void setStringValueRepresentation(@NonNull String ruleValue) {
		try {
			stringValueRepresentation = ruleValue;
			value = CSSRuleType.parseRule(getProperty().getPropertyType(), ruleValue);
		} catch (CSSRuleParseException e) {
			log.debug("Rule skipped : ".concat(ruleValue));
			log.trace("Trace : ", e);
			value = null;
			valid = false;
		}
	}

	@Override
	public String toString() {
		return String.format(FORMAT, propertyName, stringValueRepresentation);
	}
}
