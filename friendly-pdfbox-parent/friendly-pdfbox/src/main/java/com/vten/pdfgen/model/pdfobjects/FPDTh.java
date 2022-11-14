package com.vten.pdfgen.model.pdfobjects;

import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.html.HtmlTag;

public class FPDTh extends FPDTr {

	public FPDTh() {
		super();
		setTagType(HtmlTag.TH);
	}

	public FPDTh(CSSRules style) {
		super(style);
		setTagType(HtmlTag.TH);
	}
}
