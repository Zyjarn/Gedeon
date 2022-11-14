package com.vten.pdfgen.service.css.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vten.pdfgen.exception.CSSParserError;
import com.vten.pdfgen.exception.CSSParserException;
import com.vten.pdfgen.model.beans.css.CSSAttribute;
import com.vten.pdfgen.model.beans.css.CSSElement;
import com.vten.pdfgen.model.beans.css.CSSRule;
import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.css.CSSSelector;
import com.vten.pdfgen.model.beans.css.CSSStyleSheet;
import com.vten.pdfgen.model.beans.css.enumeration.CSSElementOperator;

import lombok.Getter;

public class CSSParser {

	private static final Integer IDX_ELT = 0;
	private static final Integer IDX_CLASS = 1;
	private static final Integer IDX_ID = 2;
	private static final Integer IDX_ATTRKEY = 3;
	private static final Integer IDX_ATTRVALUE = 4;
	private static final Integer IDX_QUOTE = 5;
	private static final Integer IDX_ESCAPE = 6;
	private static final Integer IDX_OPERATOR = 7;
	private static final Integer IDX_SINGLEQUOTE = 8;
	private static final Integer IDX_PARENTOPERATOR = 9;
	private static final Integer IDX_CSSRULES = 10;
	private static final Integer IDX_CSSRULENAME = 11;
	private static final Integer IDX_CSSRULEVALUE = 12;

	private boolean[] status = new boolean[] { true, false, false, false, false, false, false, false, false, false,
			false, false, false };
	private int charPos;
	private char currentChar;

	private StringBuilder currentStr = new StringBuilder();
	private List<CSSAttribute> attributes = new ArrayList<>();
	private List<CSSElement> elements = new ArrayList<>();
	private CSSSelector parentSelector = null;
	private List<CSSSelector> currentSelectors = new ArrayList<>();
	private String cssRuleName = null;
	private String operator = null;
	private String attrName = null;
	private CSSRules rules = new CSSRules();

	@Getter
	private CSSStyleSheet styleSheet = new CSSStyleSheet();

	private int lineNumber = 0;

	public void feed(String line) throws CSSParserException {
		try {
			// Increase line number counter
			lineNumber++;

			// Start iterate through line characters
			CSSCharacter csChar;
			for (charPos = 0; charPos < line.length(); charPos++) {

				// Get char and categorize it
				currentChar = line.charAt(charPos);
				csChar = CSSCharacter.fromChar(currentChar);

				switch (csChar) {
				case ALPHANUM:
					handleAlphanumCharacter();
					break;
				case ATTREND: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ELT, IDX_CLASS, IDX_ID, IDX_OPERATOR,
							IDX_PARENTOPERATOR);
					// Parse attribute
					stopAttribute();
				}
					break;
				case ATTRSTART:
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRVALUE, IDX_OPERATOR, IDX_PARENTOPERATOR);
					// Stop all other element if needed
					stopElement();
					stopClass();
					stopId();
					// Start attribute element
					status[IDX_ATTRKEY] = true;
					break;
				case CLASS: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRKEY, IDX_OPERATOR, IDX_ATTRVALUE);
					// Stop all other element if needed
					stopElement();
					stopClass();
					stopId();
					// And Start new class element
					status[IDX_CLASS] = true;
					currentStr.append(currentChar);
				}
					break;
				case COLON: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CLASS, IDX_ID, IDX_ELT, IDX_ATTRVALUE,
							IDX_OPERATOR, IDX_PARENTOPERATOR);
					if (checkStatus(IDX_QUOTE, IDX_SINGLEQUOTE)) {
						currentStr.append(currentChar);
						break;
					} else if (status[IDX_CSSRULENAME]) {
						checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRVALUE, IDX_CSSRULEVALUE);
						stopRuleName();
					}
				}
					break;
				case COMMA: {
					// switch?
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRKEY, IDX_ATTRVALUE, IDX_OPERATOR);
					if (checkStatus(IDX_QUOTE, IDX_SINGLEQUOTE, IDX_ESCAPE)) {
						currentStr.append(currentChar);
					} else if (checkStatus(IDX_ELT, IDX_CLASS, IDX_ID)) {
						// Stop all other element if needed
						stopElement();
						stopClass();
						stopId();
						// Add to selector list
						currentSelectors.add(getCurrentSelector());
						elements = new ArrayList<>();
						// Restart retrieval element
						status[IDX_ELT] = true;
					} else {
						status[IDX_OPERATOR] = true;
					}

				}
					break;
				case DASH: {
					checkStatus(CSSParserError.INVALID_ESCAPE, charPos, IDX_ESCAPE);
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_OPERATOR, IDX_PARENTOPERATOR);
					currentStr.append(currentChar);
				}
					break;
				case ELTSEPARATOR: {
					// switch?
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRKEY, IDX_ATTRVALUE, IDX_OPERATOR);
					if (checkStatus(IDX_QUOTE, IDX_SINGLEQUOTE, IDX_ESCAPE)) {
						currentStr.append(currentChar);
					} else if (checkStatus(IDX_ELT, IDX_CLASS, IDX_ID)) {
						// New parent selector
						CSSSelector selector = getCurrentSelector();
						selector.setParentSelector(parentSelector);
						parentSelector = selector;
					} else {
						status[IDX_OPERATOR] = true;
					}

				}
					break;
				case ESCAPE: {
					if (status[IDX_ESCAPE]) {
						currentStr.append(currentChar);
					} else {
						status[IDX_ESCAPE] = true;
					}
				}
					break;
				case EQUALS: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CLASS, IDX_ID, IDX_ELT, IDX_ATTRVALUE,
							IDX_PARENTOPERATOR);
					// TODO IDX_OPERATOR ?
					stopAttributeName();
					currentStr.append(currentChar);
				}
					break;
				case ID: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRKEY, IDX_OPERATOR, IDX_ATTRVALUE);
					// Stop all other element if needed
					stopElement();
					stopClass();
					stopId();
					// And Start new id element
					status[IDX_ID] = true;
					currentStr.append(currentChar);
				}
					break;
				case OPERATOR: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CLASS, IDX_ID, IDX_ELT, IDX_ATTRVALUE,
							IDX_PARENTOPERATOR);
					stopAttributeName();
					currentStr.append(currentChar);
				}
					break;
				case OTHER:
					throw new CSSParserException(CSSParserError.INVALID_CHAR, lineNumber, charPos, currentChar);
				case PARENTHESISEND: {
					currentStr.append(currentChar);
				}
					break;
				case PARENTHESISSTART: {
					currentStr.append(currentChar);
				}
					break;
				case QUOTE: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CLASS, IDX_ID, IDX_ELT, IDX_ATTRKEY,
							IDX_PARENTOPERATOR, IDX_CSSRULENAME);
					if (currentStr.toString().trim().length() == 0) {
						checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CSSRULEVALUE);
					}
					if (checkStatus(IDX_OPERATOR, IDX_SINGLEQUOTE)) {
						status[IDX_OPERATOR] = false;
						status[IDX_ATTRVALUE] = true;
						if (status[IDX_QUOTE] && !status[IDX_ESCAPE]) {
							status[IDX_QUOTE] = !status[IDX_QUOTE];
						}
					}
					currentStr.append(currentChar);
				}
					break;
				case SEMICOLON: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CLASS, IDX_ID, IDX_ELT, IDX_ATTRKEY,
							IDX_OPERATOR, IDX_PARENTOPERATOR, IDX_CSSRULENAME);
					if (status[IDX_QUOTE] || status[IDX_SINGLEQUOTE]) {
						currentStr.append(currentChar);
						break;
					} else if (status[IDX_CSSRULEVALUE]) {
						stopRuleValue();
					}
				}
					break;
				case SINGLEQUOTE: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CLASS, IDX_ID, IDX_ELT, IDX_ATTRKEY,
							IDX_PARENTOPERATOR, IDX_CSSRULENAME);
					if (currentStr.toString().trim().length() == 0) {
						checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CSSRULEVALUE);
					}
					if (status[IDX_OPERATOR] && !status[IDX_QUOTE]) {
						status[IDX_OPERATOR] = false;
						status[IDX_ATTRVALUE] = true;
						if (status[IDX_SINGLEQUOTE] && !status[IDX_ESCAPE]) {
							status[IDX_SINGLEQUOTE] = !status[IDX_SINGLEQUOTE];
						}
					}
					currentStr.append(currentChar);
				}
					break;
				case SPACE: {
					checkStatus(CSSParserError.INVALID_ESCAPE, charPos, IDX_ESCAPE);
					if (checkStatus(IDX_ATTRKEY, IDX_OPERATOR, IDX_ATTRVALUE, IDX_QUOTE, IDX_SINGLEQUOTE)) {
						currentStr.append(currentChar);
					}
					if (checkStatus(IDX_ATTRKEY, IDX_ELT, IDX_CLASS, IDX_ID)) {
						status[IDX_PARENTOPERATOR] = true;
						// Stop all other element if needed
						stopElement();
						stopClass();
						stopId();
					}
				}
					break;
				case STAR: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_CLASS, IDX_ID, IDX_ELT, IDX_ATTRVALUE,
							IDX_PARENTOPERATOR);
					status[IDX_ATTRKEY] = false;
					currentStr.append(currentChar);
				}
					break;
				case STYLEEND: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ELT, IDX_CLASS, IDX_ID, IDX_ATTRKEY,
							IDX_ATTRVALUE, IDX_OPERATOR, IDX_PARENTOPERATOR);
					stopRules();
				}
					break;
				case STYLESTART: {
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRKEY, IDX_ATTRVALUE, IDX_OPERATOR,
							IDX_PARENTOPERATOR);
					startRules();
				}
					break;
				case TILDE: {
					// switch?
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_ATTRKEY, IDX_ATTRVALUE, IDX_OPERATOR);
					if (checkStatus(IDX_QUOTE, IDX_SINGLEQUOTE, IDX_ESCAPE)) {
						currentStr.append(currentChar);
					} else if (checkStatus(IDX_ELT, IDX_CLASS, IDX_ID)) {
						// New parent selector
						CSSSelector selector = getCurrentSelector();
						selector.setParentSelector(parentSelector);
						parentSelector = selector;
					} else {
						stopAttributeName();
						currentStr.append(currentChar);
					}
				}

					break;
				case UNDERSCORE: {
					checkStatus(CSSParserError.INVALID_ESCAPE, charPos, IDX_ESCAPE);
					checkStatus(CSSParserError.INVALID_CHAR, charPos, IDX_PARENTOPERATOR);
					if (status[IDX_OPERATOR]) {
						stopAttributeOperator();
					}
					currentStr.append(currentChar);
				}
					break;
				default:
					break;
				}

				if (status[IDX_ESCAPE] && !csChar.equals(CSSCharacter.ESCAPE)) {
					status[IDX_ESCAPE] = false;
				}
			}
		} catch (RuntimeException e) {
			throw new CSSParserException(CSSParserError.UNKNOW_ERROR, lineNumber, charPos, currentChar);
		}
	}

	/**
	 * Handle alphanumeric or digit character<br/>
	 * in case of status 'operator' or 'parent operator', stop them and add
	 * character to string builder
	 * 
	 * @throws CSSParserException
	 */
	private void handleAlphanumCharacter() throws CSSParserException {
		{
			if (status[IDX_OPERATOR]) {
				stopAttributeOperator();
			} else if (status[IDX_PARENTOPERATOR]) {
				stopParentOperator();
			}
			// add char to current element
			currentStr.append(currentChar);
		}
	}

	private void checkStatus(CSSParserError error, Integer lineIndex, Integer... idx) throws CSSParserException {
		for (Integer id : idx) {
			if (status[id]) {
				throw new CSSParserException(error, lineNumber, lineIndex, id);
			}
		}
	}

	private boolean checkStatus(Integer... idx) {
		for (Integer id : idx) {
			if (status[id]) {
				return true;
			}
		}
		return false;
	}

	private CSSSelector getCurrentSelector() {

		CSSSelector selector = new CSSSelector();
		for (CSSElement elt : elements) {
			switch (elt.getType()) {
			case CLASS: {
				selector.getClassSelector().add(elt);
			}
				break;
			case ID: {
				selector.setIdSelector(elt);
			}
				break;
			case TAG: {
				selector.setElementSelector(elt);
			}
				break;
			default:
				break;
			}
		}

		return selector;
	}

	private void stopElement() {
		String elt = consumeCurrentStrBuilder();
		status[IDX_ELT] = false;
		if (StringUtils.isBlank(elt)) {
			return;
		}
		elements.add(CSSElement.parse(elt));

	}

	private void stopClass() throws CSSParserException {
		String clazz = consumeCurrentStrBuilder();
		status[IDX_CLASS] = false;
		if (StringUtils.isBlank(clazz)) {
			return;
		} else if (clazz.length() == 1) {
			throw new CSSParserException(CSSParserError.INVALID_CHAR, lineNumber, charPos, currentChar);
		}
		elements.add(CSSElement.parse(clazz));
	}

	private void stopId() throws CSSParserException {
		String id = consumeCurrentStrBuilder();
		status[IDX_ID] = false;
		if (StringUtils.isBlank(id)) {
			return;
		} else if (id.length() == 1) {
			throw new CSSParserException(CSSParserError.INVALID_CHAR, lineNumber, charPos, currentChar);
		}
		elements.add(CSSElement.parse(id));
		// Check if id already exist
	}

	private void stopParentOperator() throws CSSParserException {
		try {
			CSSElementOperator.fromString(consumeCurrentStrBuilder());
		} catch (Exception e) {
			throw new CSSParserException(CSSParserError.INVALID_OPERATOR, lineNumber, charPos, currentChar, e);
		}
		status[IDX_PARENTOPERATOR] = false;
		status[IDX_ELT] = true;
	}

	private void stopAttributeName() {
		attrName = consumeCurrentStrBuilder();
		status[IDX_OPERATOR] = true;
		status[IDX_ATTRKEY] = false;
	}

	/**
	 * Handle end of attribute operator. Just the status is changed, parsing is
	 * handle in a global attribute parser
	 */
	private void stopAttributeOperator() {
		status[IDX_OPERATOR] = false;
		status[IDX_ATTRVALUE] = true;
		operator = consumeCurrentStrBuilder();
	}

	/**
	 * Handle ']' char the CSSAttribute creation to fill 'attributes' list
	 */
	private void stopAttribute() {
		String attrValue = consumeCurrentStrBuilder();
		if (operator != null) {
			attributes.add(CSSAttributeParserUtil.parse(attrName, operator, attrValue));
			status[IDX_ATTRVALUE] = false;
		} else {
			status[IDX_ATTRKEY] = false;
			attributes.add(CSSAttributeParserUtil.parse(attrValue));
		}
		operator = null;
		attrName = null;
		status[IDX_ELT] = true;
	}

	/**
	 * Handle '}' char to stop the style definition associated to current css
	 * selectors<br/>
	 * CSS selector and style definition are added to the current stylesheet and
	 * prepare to a next css selector
	 */
	private void stopRules() {
		stopRuleValue();
		status[IDX_CSSRULES] = false;
		// Add selector and style to Stylesheet
		currentSelectors.stream().forEach(s -> styleSheet.addSelectorRule(s, rules));
		// reset objects
		rules = new CSSRules();
		currentSelectors.clear();
		elements = new ArrayList<>();
		parentSelector = null;

		// set element to true for next selector
		status[IDX_ELT] = true;

	}

	/**
	 * Handle ';' char to switch from rule value to rule name The new rule is added
	 * to the current rules list
	 */
	private void stopRuleValue() {
		rules.add(new CSSRule(cssRuleName, consumeCurrentStrBuilder()));
		cssRuleName = null;
		status[IDX_CSSRULEVALUE] = false;
		status[IDX_CSSRULENAME] = true;

	}

	/**
	 * Handle ':' char to switch from rule name to rule value
	 */
	private void stopRuleName() {
		cssRuleName = consumeCurrentStrBuilder();
		status[IDX_CSSRULEVALUE] = true;
		status[IDX_CSSRULENAME] = false;
	}

	/**
	 * Handle '{' char after css selector to define style associated to given
	 * selector.<br/>
	 * All elements are stop and css rule start
	 * 
	 * @throws CSSParserException
	 */
	private void startRules() throws CSSParserException {
		// Stop all other element if needed
		stopElement();
		stopClass();
		stopId();

		// Stop current selector
		currentSelectors.add(getCurrentSelector());
		elements = new ArrayList<>();
		// Start rules
		status[IDX_CSSRULES] = true;

		// start rule name
		status[IDX_CSSRULENAME] = true;
	}

	/**
	 * Get trimmed value of string builder instance and set is length to 0
	 * 
	 * @return trimmed value of string builder
	 */
	private String consumeCurrentStrBuilder() {
		String s = currentStr.toString().trim();
		currentStr.setLength(0);
		return s;
	}
}
