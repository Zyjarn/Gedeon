package com.vten.pdfgen.model.beans.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

public abstract class AbstractHtmlElement implements HtmlElement {

	@Getter
	protected HtmlAttributes attributes = new HtmlAttributes();
	protected String id;
	protected List<String> htmlClass;
	@Getter
	protected List<HtmlElement> childrens = new ArrayList<>();
	@Getter
	protected Optional<HtmlElement> parent;

	@Override
	public Optional<HtmlAttribute> getAttribute(String name) {
		return attributes.getAttribute(name);
	}

	@Override
	public List<String> getHtmlClass() {
		if (htmlClass != null) {
			Optional<HtmlAttribute> classAttr = attributes.getAttribute(HtmlElement.CLASS_ATTRIBUTE);
			htmlClass = Arrays.asList(classAttr.orElse(new HtmlAttribute()).getValue().trim().split(" "));
		}
		return htmlClass;
	}

	@Override
	public String getId() {
		if (id == null) {
			Optional<HtmlAttribute> idAttr = attributes.getAttribute(HtmlElement.ID_ATTRIBUTE);
			id = idAttr.orElse(new HtmlAttribute()).getValue();
		}
		return id;
	}

	@Override
	public List<HtmlElement> getChildren(String xPath) {
		// TODO Auto-generated method stub
		return null;
	}

}
