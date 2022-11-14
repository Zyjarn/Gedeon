package com.vten.pdfgen.model.beans.html;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class HtmlAttributes extends ArrayList<HtmlAttribute> {

	private static final long serialVersionUID = -3273955130139449044L;

	public Optional<HtmlAttribute> getAttribute(String name) {
		return this.stream().filter(attr -> StringUtils.equalsIgnoreCase(name, attr.getName())).findFirst();
	}

	public boolean containsAttribute(String name) {
		return this.stream().anyMatch(attr -> StringUtils.equalsIgnoreCase(name, attr.getName()));
	}
}
