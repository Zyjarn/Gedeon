package com.vten.pdfgen.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.html.HtmlAttributes;
import com.vten.pdfgen.model.beans.html.HtmlElement;
import com.vten.pdfgen.model.beans.html.HtmlTag;

import lombok.Getter;
import lombok.Setter;

public abstract class PDFObject implements HtmlElement {

	@Getter
	@Setter
	protected CSSRules style = new CSSRules();

	@Getter
	protected PDRectangle mediabox;

	@Getter
	private HtmlAttributes attributes = new HtmlAttributes();

	@Getter
	private List<String> htmlClass = new ArrayList<>();

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	protected HtmlTag tagType = HtmlTag.UNKNOW;

	/**
	 * Parent object of current object
	 */
	private HtmlElement parent;

	/**
	 * List of Childs elements of current object
	 */
	@Getter
	private List<HtmlElement> childrens = new ArrayList<>();

	protected PDFObject(HtmlTag tag) {
		tagType = tag;
	}

	protected PDFObject(HtmlTag tag, CSSRules style) {
		tagType = tag;
		setStyle(style);
	}

	/**
	 * Set the mediabox of the object and resize it height to the rendered height
	 * 
	 * @param box
	 */
	public abstract void setMediabox(PDRectangle box);

	/**
	 * Get render height of current object
	 * 
	 * @return
	 */
	public abstract float getHeight();

	/**
	 * Render the object on the given pdf document at current cursor selection
	 * 
	 * @param document
	 * @throws IOException
	 */
	public abstract void render(PDFDocument document) throws IOException;

	/**
	 * Split given object in two objects of same type (or one as empty) to get the
	 * first one that fit in the given mediabox
	 * 
	 * @param currentPageMediabox
	 * @return splitted object
	 */
	public abstract List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox);

	/*
	 * HtmlElement implementation
	 */
	@Override
	public List<HtmlElement> getChildren(String xPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<HtmlElement> getParent() {
		if (parent != null) {
			return Optional.of(parent);
		} else {
			return Optional.empty();
		}
	}

}
