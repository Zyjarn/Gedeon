package com.vten.pdfgen.model.beans.css.rulestypes.font;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CSSFontFamily {

	public static final CSSFontFamily DEFAULT = new CSSFontFamily("serif");

	@Getter
	private String name;

	@Getter
	private CSSFontFamily generic;

	public CSSFontFamily(String fontName) {
		name = fontName;
	}

}
