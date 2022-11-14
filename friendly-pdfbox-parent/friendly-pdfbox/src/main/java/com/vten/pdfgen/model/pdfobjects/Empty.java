package com.vten.pdfgen.model.pdfobjects;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.beans.html.HtmlTag;

public class Empty extends PDFObject {

	public Empty() {
		super(HtmlTag.UNKNOW);
		setMediabox(new PDRectangle());
	}

	@Override
	public void setMediabox(PDRectangle box) {
		mediabox = box;
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public void render(PDFDocument document) throws IOException {
		// Nothing to do
	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		return Arrays.asList(this);
	}

}
