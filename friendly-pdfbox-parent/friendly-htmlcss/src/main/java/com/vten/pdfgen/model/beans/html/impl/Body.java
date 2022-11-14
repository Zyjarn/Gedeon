package com.vten.pdfgen.model.beans.html.impl;

import java.util.Optional;

import com.vten.pdfgen.model.beans.html.AbstractHtmlElement;
import com.vten.pdfgen.model.beans.html.HtmlElement;
import com.vten.pdfgen.model.beans.html.HtmlTag;

public class Body extends AbstractHtmlElement {

	@Override
	public HtmlTag getTagType() {
		return HtmlTag.BODY;
	}

	@Override
	public Optional<HtmlElement> getParent() {
		return Optional.empty();
	}

}
