package com.vten.pdfgen.model.beans.html;

import java.util.List;
import java.util.Optional;

public interface HtmlElement {

	public static final String CLASS_ATTRIBUTE = "class";
	public static final String ID_ATTRIBUTE = "id";

	public HtmlAttributes getAttributes();

	public default Optional<HtmlAttribute> getAttribute(String name) {
		return getAttributes().getAttribute(name);
	}

	public List<String> getHtmlClass();

	public String getId();

	public List<HtmlElement> getChildrens();

	public List<HtmlElement> getChildren(String xPath);

	public Optional<HtmlElement> getParent();

	public HtmlTag getTagType();

}
