package com.vten.pdfgen.service.css.util;

public enum CSSCharacter {
	ALPHANUM, CLASS('.'), ID('#'), ATTRSTART('['), ATTREND(']'), COMMA(','), ELTSEPARATOR('>', '+'), ESCAPE('\\'),
	TILDE('~'), OPERATOR('|', '^', '$'), EQUALS('='), QUOTE('"'), SINGLEQUOTE('\''), COLON(':'), PARENTHESISSTART('('),
	PARENTHESISEND(')'), STAR('*'), SPACE(' ', '\t', '\n'), UNDERSCORE('_'), DASH('-'), STYLESTART('{'), STYLEEND('}'),
	SEMICOLON(';'), OTHER;

	private Character[] cs;

	CSSCharacter(Character... args) {
		cs = args;
	}

	public static CSSCharacter fromChar(char c) {
		if (Character.isAlphabetic(c) || Character.isDigit(c)) {
			return ALPHANUM;
		}
		for (CSSCharacter character : CSSCharacter.values()) {
			if (contains(character, c)) {
				return character;
			}
		}
		return OTHER;
	}

	private static boolean contains(CSSCharacter character, char ch) {
		if (character.cs != null) {
			for (Character tmpC : character.cs) {
				if (tmpC.equals(ch)) {
					return true;
				}
			}
		}
		return false;
	}

}
