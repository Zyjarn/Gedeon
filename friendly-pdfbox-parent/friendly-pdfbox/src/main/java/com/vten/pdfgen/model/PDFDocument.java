package com.vten.pdfgen.model;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.css.CSSStyleSheet;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSLengthRule;
import com.vten.pdfgen.model.beans.css.rulestypes.CSSQuartetRule;
import com.vten.pdfgen.model.pdfobjects.Empty;
import com.vten.pdfgen.model.pdfobjects.PDFImage;
import com.vten.pdfgen.model.pdfobjects.PDFParagraph;
import com.vten.pdfgen.model.pdfobjects.PDFTable;
import com.vten.pdfgen.model.pdfobjects.PDFTitle;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PDFDocument implements AutoCloseable {

	private static final String DEFAULT_FILENAME = "DEFAULT.pdf";

	@Getter
	private PDDocument document = new PDDocument();
	@Getter
	@Setter
	private String filename;

	// Space beetween objects
	private float leading = 0;
	@Getter
	private PDPage page;

	@Getter
	private int currentPageIndex = 0;

	@Getter
	private float heightCursor = 0;

	@Getter
	private CSSStyleSheet stylesheet = new CSSStyleSheet();

	// TODO to remove and handle a body object ?
	@Getter
	@Setter
	private CSSRules style = defaultPageStyle();

	public PDFDocument() {
		document = new PDDocument();
		addPage();
	}

	/**
	 * Get the default style for the document. Consist of css default style with
	 * margin of 25px
	 * 
	 * @return default style instance
	 */
	private static CSSRules defaultPageStyle() {
		CSSRules defaultStyle = new CSSRules();
		defaultStyle.setMargin("25px 25px 25px 25px");
		return defaultStyle;
	}

	@Override
	public void close() throws IOException {
		document.close();
	}

	/**
	 * Save document to document filename. If not set, use default filename
	 * "DEFAULT.pdf" and save in process execution folder
	 * 
	 * @throws IOException in case of failed save
	 */
	public void save() throws IOException {
		document.save(filename);
	}

	/**
	 * Save document on given file
	 * 
	 * @param fileUri
	 * @throws IOException in case of failed save
	 */
	public void saveAs(String fileUri) throws IOException {
		filename = fileUri;
		if (StringUtils.isBlank(filename)) {
			filename = DEFAULT_FILENAME;
		}
		document.save(new File(filename));
	}

	/**
	 * Save document on given file
	 * 
	 * @param fileStream
	 * @throws IOException in case of failed save
	 */
	public void saveAs(OutputStream fileStream) throws IOException {
		document.save(fileStream);
	}

	/**
	 * Get number of pages for current document
	 * 
	 * @return
	 */
	public int getPageCount() {
		return document.getNumberOfPages();
	}

	/**
	 * Add new empty page to current document with default A4 format Next object
	 * will be added at top of this page.
	 */
	public void addPage() {
		addPage(currentPageIndex + 1, PDRectangle.A4);

	}

	/**
	 * Add new empty page to current document with given format and at given index.
	 * Next object will be added at top of this page and next pages added will
	 * following this one.
	 * 
	 * @param index
	 * @param size
	 */
	public void addPage(final int index, final PDRectangle size) {
		// Use the normal method if the page comes after all other pages
		PDPage newPage = new PDPage(size);
		if (index >= document.getNumberOfPages()) {
			document.addPage(newPage);
		} else {
			document.getPages().insertBefore(newPage, document.getDocumentCatalog().getPages().get(index));
		}
		page = newPage;
		// TODO handle other than px
		heightCursor = newPage.getMediaBox().getHeight() - style.getMargin().getUp().getValue();
		currentPageIndex = index;
	}

	/**
	 * Get the mediabox height for the current page
	 * 
	 * @return mediabox height - margin
	 */
	public float getCurrentPageMediaHeight() {
		// TODO handle other than px
		return page.getMediaBox().getHeight()
				- (style.getMargin().getUp().getValue() + style.getMargin().getDown().getValue());
	}

	/**
	 * Add a PDFObject to the pdf at current document position if object exceed
	 * remaining page size, new pages are added
	 * 
	 * @param pdfObject to add on document
	 */
	public void addPDFObject(PDFObject pdfObject) {
		try {
			// Retrieve object style from stylesheet
			CSSRules rules = stylesheet.getStyle(pdfObject);
			// And add it to object
			pdfObject.setStyle(rules);

			// Check remaining space on page
			if (pdfObject.getMediabox() == null) {
				if (getCurrentMediabox().getHeight() <= 0) {
					// add page if no remaining place
					addPage();
				}
				pdfObject.setMediabox(getCurrentMediabox());
			}

			// Split object and add new page if needed to render it
			List<PDFObject> list = pdfObject.splitObjectForRendition(getCurrentMediabox());
			if (list.size() == 1) {
				list.get(0).render(this);
				heightCursor = heightCursor - list.get(0).getHeight() - leading;
			} else {
				PDFObject obj = list.get(0);
				obj.render(this);
				addPage();
				if (!(list.get(1) instanceof Empty)) {
					addPDFObject(list.get(1));
				}
			}
		} catch (IOException e) {
			log.error("Unexpected issue while adding object to pdf document", e);
		}
	}

	/**
	 * Add an title to the pdf at current document position
	 * 
	 * @param text of the title
	 * @param type for title style
	 */
	public void addTitle(String text, PDFTitle.Type type) {
		log.debug("Add Title");
		addPDFObject(new PDFTitle(text, type));
	}

	/**
	 * Add an image to the pdf at current document position
	 * 
	 * @param text of the paragraph
	 */
	public void addParagraph(String text) {
		log.debug("Add Paragraph");
		addPDFObject(new PDFParagraph(text));
	}

	/**
	 * Add an table to the pdf at current document position
	 * 
	 * @param table
	 * @throws IOException
	 */
	public void addTable(PDFTable table) throws IOException {
		log.debug("Add Table");
		addPDFObject(table);
	}

	/**
	 * Add an image to the pdf at current document position
	 * 
	 * @param filename path to image, must be accessible
	 */
	public void addImage(String filename) {
		log.debug("Add Image");
		try {
			addPDFObject(new PDFImage(filename, this));
		} catch (IOException e) {
			log.error(String.format("Unable to load image '%s'. ", filename), e);
		}
	}

	/**
	 * Get mediabox of remaining space on page
	 * 
	 * @return pdrectangle
	 */
	private PDRectangle getCurrentMediabox() {
		PDRectangle mediabox = page.getMediaBox();
		// TODO handle other than px
		CSSQuartetRule<CSSLengthRule> margin = style.getMargin();
		float width = mediabox.getWidth() - (margin.getLeft().getValue() + margin.getRight().getValue());
		float height = heightCursor - margin.getDown().getValue();
		float startX = mediabox.getLowerLeftX() + margin.getLeft().getValue();
		float startY = mediabox.getLowerLeftY() + margin.getDown().getValue();
		return new PDRectangle(startX, startY, width, height);
	}
}
