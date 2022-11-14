package com.vten.pdfgen.model.beans.html.impl;

import com.vten.pdfgen.model.beans.html.AbstractHtmlElement;
import com.vten.pdfgen.model.beans.html.HtmlTag;

public class Image extends AbstractHtmlElement {

	@Override
	public HtmlTag getTagType() {
		return HtmlTag.IMG;
	}

}
