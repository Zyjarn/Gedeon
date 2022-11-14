package com.vten.pdfgen.model.pdfobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.html.HtmlTag;

import lombok.Getter;
import lombok.Setter;

public class FPDTr extends PDFObject {
	@Getter
	@Setter
	List<FPDTd> cells = new ArrayList<>();
	@Setter
	float height;
	float width;

	float padding = 5;

	public FPDTr() {
		super(HtmlTag.TR);
	}

	public FPDTr(CSSRules style) {
		super(HtmlTag.TR, style);
	}

	@Override
	public void setMediabox(PDRectangle box) {
		this.mediabox = box;
		float x;
		float y = mediabox.getLowerLeftY();
		float cellWidth = (mediabox.getWidth() / cells.size());
		FPDTd cell;
		for (int i = 0; i < cells.size(); i++) {
			cell = cells.get(i);
			// Check height to create new page

			// Get x position from width and column number
			x = mediabox.getLowerLeftX() + (i * (mediabox.getWidth() / cells.size()));

			// Set mediabox with all available space below
			cell.setMediabox(new PDRectangle(x, y, cellWidth, mediabox.getHeight()));

		}
		// Retrieve max row height
		float rowHeight = cells.stream().map(FPDTd::getHeight).max(Double::compare).orElse(0f);
		// resize row mediabox with real content size
		mediabox.setLowerLeftY(mediabox.getUpperRightY() - rowHeight);
		// And resize other cell mediabox that does not have row size
		cells.stream().forEach(cl -> cl.getMediabox().setLowerLeftY(cl.getMediabox().getUpperRightY() - rowHeight));
	}

	@Override
	public void render(PDFDocument document) throws IOException {
		for (FPDTd cell : cells) {
			cell.render(document);
		}
	}

	@Override
	public float getHeight() {
		return mediabox.getHeight();
	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		if ((getHeight() > currentPageMediabox.getHeight()) && !cells.isEmpty()) {
			List<PDFObject> list;
			FPDTr row1 = new FPDTr(style);
			FPDTr row2 = new FPDTr(style);
			int i = 0;
			float x;
			float y = mediabox.getLowerLeftY();
			float cellWidth = (mediabox.getWidth() / cells.size());
			for (FPDTd cell : cells) {
				x = mediabox.getLowerLeftX() + (i * (mediabox.getWidth() / cells.size()));

				list = cell.splitObjectForRendition(new PDRectangle(x, y, cellWidth, currentPageMediabox.getHeight()));
				row1.getCells().add((FPDTd) list.get(0));

				row2.getCells().add(list.size() > 1 ? (FPDTd) list.get(1) : new FPDTd(getStyle(), new Empty()));
				i++;
			}
			row1.setMediabox(currentPageMediabox);

			return Arrays.asList(row1, row2);
		} else {
			return Arrays.asList(this);
		}

	}
}
