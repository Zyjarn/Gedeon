package com.vten.pdfgen.service.css.util;

import java.util.regex.Pattern;

import com.vten.pdfgen.model.beans.css.CSSAttribute;
import com.vten.pdfgen.model.beans.css.enumeration.CSSAttrOp;

public class CSSAttributeParserUtil {

	private static final String REGEX_OPERATOR = "([\\*~$^\\|]){0,1}=";
	private static final Pattern PATTERN_OPERATOR = Pattern.compile(REGEX_OPERATOR);

	private CSSAttributeParserUtil() {
		// Nothing to do, access static way
	}

	/**
	 * Get a CSSAttribute instance from given string
	 * 
	 * @param str string to parse
	 * @return new instance
	 */
	public static CSSAttribute parse(String str) {
		return new CSSAttribute(str);
//		if (StringUtils.isBlank(str)) {
//			throw new IllegalArgumentException();
//		}
//		CSSAttribute attribute = new CSSAttribute("");
//		// remove bracket to parse key, value and operator
//		String cleanStr = cleanStartEnd(str, "[", "]");
//
//		// Retrieve operator from pattern
//		Matcher matchOperator = PATTERN_OPERATOR.matcher(cleanStr);
//		if (!matchOperator.find()) {
//			attribute.setOperator(CSSAttrOp.NONE);
//			attribute.setKey(cleanStr);
//		} else {
//			attribute.setOperator(CSSAttrOp.fromString(matchOperator.group()));
//			attribute.setKey(cleanStr.substring(0, matchOperator.start()));
//			attribute.setValue(cleanStartEnd(cleanStr.substring(matchOperator.end()), "\"", "\""));
//		}
//		return attribute;
	}

	/**
	 * Remove start and end strings from the begining and the end of the given
	 * string if they exist
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	private static String cleanStartEnd(String str, String start, String end) {
		String cleanStr = str;
		if (str.startsWith(start)) {
			cleanStr = cleanStr.substring(1);
		}
		if (str.endsWith(end)) {
			cleanStr = cleanStr.substring(0, cleanStr.length() - 1);
		}
		return cleanStr;
	}

	public static CSSAttribute parse(String attrName, String operator, String attrValue) {
		return new CSSAttribute(operator, attrValue, CSSAttrOp.fromString(operator));
	}
}
