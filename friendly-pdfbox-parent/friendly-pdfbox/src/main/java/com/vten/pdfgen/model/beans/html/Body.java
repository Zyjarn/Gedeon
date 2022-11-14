package com.vten.pdfgen.model.beans.html;

import java.util.Optional;

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
