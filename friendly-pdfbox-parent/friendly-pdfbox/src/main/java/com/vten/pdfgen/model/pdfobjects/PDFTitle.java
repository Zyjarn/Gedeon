package com.vten.pdfgen.model.pdfobjects;

import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontStyle;
import com.vten.pdfgen.model.beans.css.rulestypes.font.CSSFontWeight;
import com.vten.pdfgen.model.beans.html.HtmlTag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDFTitle extends PDFParagraph {

	public enum Type {
		H1, H2, H3, H4, H5, H6
	}

	public enum CounterType {
		NUMERAL, LETTER, NONE
	}

	private Type type = Type.H1;
	private CounterType counterType = CounterType.NONE;

	public PDFTitle(String titleText, Type type) {
		super(titleText/* , getTitleStyle(type) */);
		switch (type) {
		case H1:
			tagType = HtmlTag.H1;
			break;
		case H2:
			tagType = HtmlTag.H2;
			break;
		case H3:
			tagType = HtmlTag.H3;
			break;
		case H4:
			tagType = HtmlTag.H4;
			break;
		case H5:
			tagType = HtmlTag.H5;
			break;
		case H6:
			tagType = HtmlTag.H6;
			break;
		default:
			tagType = HtmlTag.UNKNOW;
			break;
		}
		setType(type);
	}

	public static CSSRules getTitleStyle(Type type) {
		CSSRules s = new CSSRules();
		switch (type) {
		case H1: {
			s.setFontSize("32px");
		}
			break;
		case H2: {
			s.setFontSize("30px");
		}
			break;
		case H3: {
			s.setFontSize("28px");
		}
			break;
		case H4: {
			s.setFontSize("24px");
			s.setFontStyle(CSSFontStyle.ITALIC);
		}
			break;
		case H5: {
			s.setFontSize("20px");
		}
			break;
		case H6:
		default: {
			s.setFontSize("18px");
			s.setFontStyle(CSSFontStyle.ITALIC);
		}
			break;
		}
		s.setFontWeight(CSSFontWeight.BOLD);
		return s;
	}
}
