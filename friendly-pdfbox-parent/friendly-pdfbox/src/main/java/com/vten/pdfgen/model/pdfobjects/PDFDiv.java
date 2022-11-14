package com.vten.pdfgen.model.pdfobjects;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.beans.html.HtmlTag;

public class PDFDiv extends PDFObject {

	protected PDFDiv() {
		super(HtmlTag.DIV);
	}

	@Override
	public void setMediabox(PDRectangle box) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(PDFDocument document) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		// TODO Auto-generated method stub
		return null;
	}

}
