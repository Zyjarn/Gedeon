package com.vten.pdfgen.model.pdfobjects;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSFontRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSLengthRule;
import com.vten.pdfgen.model.beans.html.HtmlTag;

import lombok.Getter;
import lombok.NonNull;

public class Line extends PDFObject {

	@Getter
	private final String text;

	private float leading = 1.2f;

	protected Line(@NonNull String text, CSSRules style) {
		super(HtmlTag.UNKNOW);
		this.text = text;
	}

	@Override
	public void setMediabox(PDRectangle box) {
		this.mediabox = box;
		float height = style.getFontSize().getValue() * leading;
		mediabox.setLowerLeftY(mediabox.getUpperRightY() - height);
	}

	@Override
	public float getHeight() {
		return mediabox.getHeight();
	}

	@Override
	public void render(PDFDocument document) throws IOException {
		float startX = getMediabox().getLowerLeftX();
		float startY = getMediabox().getUpperRightY() - style.getFontSize().getValue();

		try (PDPageContentStream stream = new PDPageContentStream(document.getDocument(), document.getPage(),
				AppendMode.APPEND, true)) {

			stream.beginText();
			// TODO handle other than px
			CSSFontRule fr = style.getFont();
			CSSLengthRule rule = style.getFontSize();
			stream.setFont(getFont(), style.getFontSize().getValue());
			stream.newLineAtOffset(startX, startY);
			stream.showText(text);
			stream.endText();

		}
	}

	private PDFont getFont() {
		/*
		 * if (style.isBold() && style.isItalic()) { return
		 * style.getFont().getFontBoldItalic(); } else if (style.isBold()) { return
		 * style.getFont().getFontBold(); } else if (style.isItalic()) { return
		 * style.getFont().getFontItalic(); } else { return style.getFont().getFont(); }
		 */
		// TODO retrieve font from style
		return PDType1Font.HELVETICA;
	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		// return empty and this if currentPageMediabox Height < Img height
		return Arrays.asList(this);
	}

}
