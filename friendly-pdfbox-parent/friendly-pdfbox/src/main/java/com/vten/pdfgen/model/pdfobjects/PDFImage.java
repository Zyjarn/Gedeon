package com.vten.pdfgen.model.pdfobjects;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.html.HtmlTag;

public class PDFImage extends PDFObject {

	private PDImageXObject imageObject;

	private PDFDocument document;

	public PDFImage(String filename, PDFDocument pdfdocument) throws IOException {
		super(HtmlTag.IMG);
		document = pdfdocument;
		this.imageObject = PDImageXObject.createFromFile(filename, document.getDocument());
	}

	public PDFImage(String filename, PDFDocument pdfdocument, CSSRules style) throws IOException {
		super(HtmlTag.IMG, style);
		document = pdfdocument;
		this.imageObject = PDImageXObject.createFromFile(filename, document.getDocument());
	}

	@Override
	public void setMediabox(PDRectangle box) {
		this.mediabox = box;
		// Resize mediabox with real height

		float width = mediabox.getWidth();
		float height = mediabox.getHeight();
		if ((imageObject.getHeight() < mediabox.getHeight()) && (imageObject.getWidth() < mediabox.getWidth())) {
			// case image fit given box
			// mediabox is resize to image size
			width = imageObject.getWidth();
			height = imageObject.getHeight();
		} else if ((imageObject.getHeight() < mediabox.getHeight()) && (imageObject.getWidth() > mediabox.getWidth())) {
			// case larger
			height = imageObject.getHeight() * (mediabox.getWidth() / imageObject.getWidth());
		} else if ((imageObject.getHeight() > mediabox.getHeight()) && (imageObject.getWidth() < mediabox.getWidth())) {
			// case higher
			width = imageObject.getWidth() * (mediabox.getHeight() / imageObject.getHeight());
		} else {
			// case larger and higher
			float ratioHeight = (mediabox.getHeight() / imageObject.getHeight());
			float ratioWidth = (mediabox.getWidth() / imageObject.getWidth());
			if (ratioHeight > ratioWidth) {
				// case larger than higher
				height = imageObject.getHeight() * ratioWidth;
			} else {
				// case higher than larger
				width = imageObject.getWidth() * ratioHeight;

			}
		}

		mediabox.setUpperRightX(mediabox.getLowerLeftX() + width);
		mediabox.setLowerLeftY(mediabox.getUpperRightY() - height);
	}

	@Override
	public float getHeight() {
		return mediabox.getHeight();
	}

	@Override
	public void render(PDFDocument document) throws IOException {
		try (PDPageContentStream stream = new PDPageContentStream(document.getDocument(), document.getPage(),
				AppendMode.APPEND, true)) {

			stream.saveGraphicsState();
			if (style.getOpacity().getValue() < 1F) {
				PDExtendedGraphicsState pdExtGfxState = new PDExtendedGraphicsState();
				pdExtGfxState.setBlendMode(BlendMode.MULTIPLY);
				pdExtGfxState.setNonStrokingAlphaConstant(style.getOpacity().getValue());
				stream.setGraphicsStateParameters(pdExtGfxState);
			}
			stream.drawImage(imageObject, mediabox.getLowerLeftX(), mediabox.getLowerLeftY(), mediabox.getWidth(),
					mediabox.getHeight());

			stream.restoreGraphicsState();

		}
	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		if (((imageObject.getHeight() < currentPageMediabox.getHeight())
				&& (imageObject.getWidth() < currentPageMediabox.getWidth()))
				|| ((imageObject.getHeight() < currentPageMediabox.getHeight())
						&& (imageObject.getWidth() > currentPageMediabox.getWidth()))) {
			// case image fit given box
			return Arrays.asList(this);
		} else if ((imageObject.getHeight() > currentPageMediabox.getHeight())
				&& (imageObject.getWidth() < currentPageMediabox.getWidth())) {
			// case higher
			return Arrays.asList(new Empty(), this);
		} else {
			// case larger and higher
			float ratioHeight = (currentPageMediabox.getHeight() / imageObject.getHeight());
			float ratioWidth = (currentPageMediabox.getWidth() / imageObject.getWidth());
			if (ratioHeight > ratioWidth) {
				// case larger than higher
				return Arrays.asList(this);
			} else {
				// case higher than larger
				return Arrays.asList(new Empty(), this);

			}

		}
	}
}
