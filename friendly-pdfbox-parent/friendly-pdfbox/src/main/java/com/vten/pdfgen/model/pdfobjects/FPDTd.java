package com.vten.pdfgen.model.pdfobjects;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.beans.PDFUtil;
import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.css.enumeration.CSSBorderType;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSLengthRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSQuartetRule;
import com.vten.pdfgen.model.beans.html.HtmlTag;

import lombok.Getter;

public class FPDTd extends PDFObject {

	@Getter
	private PDFObject cellContent;

	public FPDTd(PDFObject content) {
		super(HtmlTag.TD);
		this.cellContent = content;
	}

	/**
	 * Create cell with given content
	 * 
	 * @param style   style for cell
	 * @param content
	 */
	public FPDTd(CSSRules style, PDFObject content) {
		super(HtmlTag.TD, style);
		this.cellContent = content;
	}

	@Override
	public float getHeight() {
		// Keep calculus cause it's used in setMediabox of row when mediabox take all
		// remaining space
		// TODO handle rules other than px
		CSSQuartetRule<CSSLengthRule> margin = style.getMargin();
		float marginValue = margin.getUp().getValue() + margin.getDown().getValue();
		CSSQuartetRule<CSSLengthRule> padding = style.getPadding();
		float paddingValue = padding.getUp().getValue() + padding.getDown().getValue();
		// twice value cause margin is already out of mediabox bound
		return this.cellContent.getHeight() + (2 * marginValue) + (2 * paddingValue);
	}

	@Override
	public void setMediabox(PDRectangle box) {
		this.mediabox = box;
		cellContent.setMediabox(getContentCellMediabox(mediabox));
		// Update mediabox with real height
		CSSQuartetRule<CSSLengthRule> margin = style.getMargin();
		CSSQuartetRule<CSSLengthRule> padding = style.getPadding();
		mediabox.setLowerLeftY(((cellContent.getMediabox().getLowerLeftY() - (margin.getDown().getValue()))
				+ padding.getDown().getValue()));
	}

	@Override
	public void render(PDFDocument document) throws IOException {
		// Render cell style

		try (PDPageContentStream stream = new PDPageContentStream(document.getDocument(), document.getPage(),
				AppendMode.APPEND, true)) {
			stream.saveGraphicsState();
			// Add cell to stream path
			PDRectangle cellBorder = getMediaboxWithoutMargin(mediabox);

			// Render background color
			if (!PDFUtil.COLOR_TRANSPARENT.equals(style.getBackgroundColor().getValue())) {
				stream.setNonStrokingColor(style.getBackgroundColor().getValue());
				stream.addRect(cellBorder.getLowerLeftX(), cellBorder.getLowerLeftY(), cellBorder.getWidth(),
						cellBorder.getHeight());
				stream.fill();
			}

			// Render border
			if (!CSSBorderType.HIDDEN.equals(style.getBorder().getType())) {
				// TODO handle others types of border, maybe in a border renderer ?
				stream.setStrokingColor(style.getBorder().getColor().getValue());
				stream.addRect(cellBorder.getLowerLeftX(), cellBorder.getLowerLeftY(), cellBorder.getWidth(),
						cellBorder.getHeight());
				stream.stroke();
			}

			// Revert stroking to transparent
			stream.restoreGraphicsState();

		}

		// Then render cell content
		cellContent.render(document);

	}

	@Override
	public List<PDFObject> splitObjectForRendition(PDRectangle currentPageMediabox) {
		PDRectangle newMediabox = getContentCellMediabox(currentPageMediabox);
		List<PDFObject> list = cellContent.splitObjectForRendition(newMediabox).stream().map(c -> new FPDTd(c))
				.collect(Collectors.toList());
		list.get(0).setMediabox(newMediabox);
		return list;
	}

	/**
	 * Define mediabox from given rectangle substracted margin and padding
	 * 
	 * @param box
	 * @return mediabox for content
	 */
	protected PDRectangle getContentCellMediabox(PDRectangle box) {
		return getMediaboxWithoutMargin(getMediaboxWithoutPadding(box));
	}

	/**
	 * Define mediabox from given rectangle substracted margin
	 * 
	 * @param box
	 * @return mediabox for content
	 */
	protected PDRectangle getMediaboxWithoutMargin(PDRectangle box) {
		// TODO handle rules other than px
		CSSQuartetRule<CSSLengthRule> margin = style.getMargin();
		return new PDRectangle(box.getLowerLeftX() + (margin.getLeft().getValue()),
				box.getLowerLeftY() + (margin.getDown().getValue()),
				box.getWidth() - (margin.getLeft().getValue() + margin.getRight().getValue()),
				box.getHeight() - (margin.getUp().getValue() + margin.getDown().getValue()));
	}

	/**
	 * Define mediabox from given rectangle substracted padding
	 * 
	 * @param box
	 * @return mediabox for content
	 */
	protected PDRectangle getMediaboxWithoutPadding(PDRectangle box) {
		// TODO handle rules other than px
		CSSQuartetRule<CSSLengthRule> padding = style.getPadding();
		return new PDRectangle(box.getLowerLeftX() + (padding.getLeft().getValue()),
				box.getLowerLeftY() + (padding.getDown().getValue()),
				box.getWidth() - (padding.getLeft().getValue() + padding.getRight().getValue()),
				box.getHeight() - (padding.getUp().getValue() + padding.getDown().getValue()));
	}
}
