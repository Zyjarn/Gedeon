package com.vten.pdfgen.model.pdfobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.vten.pdfgen.exception.UnsupportedPDFException;
import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.html.HtmlTag;

public class PDFParagraph extends PDFObject {

	private String text;
	private List<Line> lines;

	public PDFParagraph(String paragraphText) {
		super(HtmlTag.P);
		this.text = paragraphText;
	}

	public PDFParagraph(CSSRules style, String paragraphText) {
		super(HtmlTag.P, style);
		this.text = paragraphText;
	}

	@Override
	public void setMediabox(PDRectangle box) {
		this.mediabox = box;

		getLines();
		float startX = getMediabox().getLowerLeftX();
		// TODO handle other than px
		float startY = getMediabox().getUpperRightY() - style.getFontSize().getValue();
		Line line;
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			// TODO replace 1.2 with line height
			line.setMediabox(
					new PDRectangle(startX, startY, mediabox.getWidth(), style.getFontSize().getValue() * 1.2f));

			startY -= style.getFontSize().getValue() * 1.2f;
		}
		float height = (float) lines.stream().map(Line::getHeight).mapToDouble(Float::doubleValue).sum();
		mediabox.setLowerLeftY(mediabox.getUpperRightY() - height);
	}

	public List<Line> getLines() {
		if (lines != null) {
			return lines;
		}
		if (getMediabox() == null) {
			throw new UnsupportedPDFException("getLines can't be accessed with null width.");
		}
		loadLinesFromCurrentMediabox();
		return lines;
	}

	private void loadLinesFromCurrentMediabox() {
		lines = new ArrayList<>();

		try {
			for (String subParagraph : text.split("\n")) {
				loadLinesFromCurrentMediabox(subParagraph);
			}
		} catch (IOException e) {
			throw new UnsupportedPDFException("getLines, unexpected issue while determine text size with font.", e);
		}
	}

	/**
	 * 
	 * @param subParagraph
	 * @return
	 * @throws IOException
	 */
	private void loadLinesFromCurrentMediabox(String subParagraph) throws IOException {
		int lastSpace = -1;
		int spaceIndex;
		float size;
		String subString;
		while (subParagraph.length() > 0) {
			spaceIndex = subParagraph.indexOf(' ', lastSpace + 1);
			if (spaceIndex < 0) {
				spaceIndex = subParagraph.length();
			}
			subString = subParagraph.substring(0, spaceIndex);

			// TODO handle italic and bold and others than px and use font front style
			size = (style.getFontSize().getValue() * PDType1Font.HELVETICA.getStringWidth(subString)) / 1000;

			if (size > getMediabox().getWidth()) {
				if (lastSpace < 0) {
					lastSpace = spaceIndex;
				}
				subString = subParagraph.substring(0, lastSpace);
				lines.add(new Line(subString, style));
				subParagraph = subParagraph.substring(lastSpace).trim();

				lastSpace = -1;
			} else if (spaceIndex == subParagraph.length()) {
				lines.add(new Line(subParagraph, style));
				subParagraph = "";
			} else {
				lastSpace = spaceIndex;
			}
		}

		// Apply style on all line
		lines.stream().forEach(line -> line.setStyle(style));
	}

	@Override
	public float getHeight() {
		return mediabox.getHeight();
	}

	@Override
	public void render(PDFDocument document) throws IOException {
		for (Line line : getLines()) {
			line.render(document);
		}
	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		if (getHeight() > currentPageMediabox.getHeight()) {
			List<PDFObject> list = new ArrayList<>();

			int i = 0;
			// TODO handle other than px and use line height
			float height = style.getMargin().getUp().getValue();
			while ((height = height + (style.getFontSize().getValue() * 1.2f)) < currentPageMediabox.getHeight()) {
				i++;
			}
			if (i == 0) {
				list.add(new Empty());
				list.add(this);
			} else {
				PDFParagraph paragraphCurrentPage = new PDFParagraph(style, String.join(" ",
						getLines().subList(0, i).stream().map(Line::getText).collect(Collectors.toList())));
				paragraphCurrentPage.setMediabox(currentPageMediabox);
				list.add(paragraphCurrentPage);

				PDFParagraph paragraphNextPage = new PDFParagraph(style, String.join(" ", getLines()
						.subList(i, getLines().size()).stream().map(Line::getText).collect(Collectors.toList())));
				list.add(paragraphNextPage);
			}

			return list;
		} else {
			return Arrays.asList(this);
		}
	}

}
