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

public class PDFTable extends PDFObject {

	@Getter
	private List<FPDTr> rows = new ArrayList<>();

	public PDFTable() {
		super(HtmlTag.TABLE);
	}

	public PDFTable(CSSRules style) {
		super(HtmlTag.TABLE, style);
	}

	@Override
	public float getHeight() {
		return rows.isEmpty() ? 0 : rows.stream().map(FPDTr::getHeight).mapToLong(Float::intValue).sum();
	}

	@Override
	public void setMediabox(PDRectangle box) {
		this.mediabox = box;
		float x = mediabox.getLowerLeftX();
		float y = mediabox.getLowerLeftY();
		float width = mediabox.getWidth();
		float prevRowHeight = 0;
		float tableHeight = 0;
		for (FPDTr row : rows) {
			// Set mediabox with all available space below
			row.setMediabox(new PDRectangle(x, y, width, mediabox.getHeight() - tableHeight));
			prevRowHeight = row.getMediabox().getHeight();
			tableHeight += prevRowHeight;
		}
		// resize row mediabox with real content size
		mediabox.setLowerLeftY(mediabox.getUpperRightY() - tableHeight);
	}

	@Override
	public void render(PDFDocument document) throws IOException {

		FPDTr row;
		for (int i = 0; i < rows.size(); i++) {
			row = rows.get(i);
			row.render(document);
		}
	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		if ((getHeight() > currentPageMediabox.getHeight()) && !rows.isEmpty()) {
			float cursor = 0;
			FPDTr currentRow = rows.get(0);
			int i = 0;
			for (; i < rows.size(); i++) {
				currentRow = rows.get(i);
				if ((cursor + currentRow.getHeight()) > currentPageMediabox.getHeight()) {
					break;
				}
				cursor += currentRow.getHeight();
			}

			PDRectangle newMediaBox = new PDRectangle(currentPageMediabox.getLowerLeftX(),
					currentPageMediabox.getLowerLeftY(), currentPageMediabox.getWidth(),
					currentPageMediabox.getHeight() - cursor);

			List<PDFObject> splitRow = currentRow.splitObjectForRendition(newMediaBox);

			PDFTable t1 = new PDFTable(style);
			t1.getRows().addAll(rows.subList(0, i));
			t1.getRows().add((FPDTr) splitRow.get(0));
			t1.setMediabox(currentPageMediabox);
			PDFTable t2 = new PDFTable(style);
			if (splitRow.size() > 1) {
				t2.getRows().add((FPDTr) splitRow.get(1));
				t2.getRows().addAll(rows.subList(i + 1, rows.size()));

			}

			return Arrays.asList(t1, t2);
		} else {
			return Arrays.asList(this);
		}
	}
}
