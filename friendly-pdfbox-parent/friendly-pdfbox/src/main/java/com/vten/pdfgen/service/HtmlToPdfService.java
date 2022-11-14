package com.vten.pdfgen.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.vten.pdfgen.exception.UnsuportedHtmlException;
import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.PDFObject;
import com.vten.pdfgen.model.pdfobjects.Empty;
import com.vten.pdfgen.model.pdfobjects.FPDTd;
import com.vten.pdfgen.model.pdfobjects.PDFParagraph;
import com.vten.pdfgen.model.pdfobjects.FPDTr;
import com.vten.pdfgen.model.pdfobjects.PDFTable;
import com.vten.pdfgen.model.pdfobjects.PDFTitle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HtmlToPdfService {

	/**
	 * Get a PDFDocument instance filled with the given html file
	 * 
	 * @param filename path to an html file
	 * @return pdf representation of given file
	 * @throws
	 * @throws Exception
	 */
	public PDFDocument loadDocument(String filename) throws UnsuportedHtmlException {
		try (FileInputStream outputStream = new FileInputStream(filename)) {
			return loadDocument(outputStream);
		} catch (IOException e) {
			throw new UnsuportedHtmlException("Can't load html file. ", e);
		}
	}

	/**
	 * Get a PDFDocument instance filled with the given file stream represent an
	 * html document file
	 * 
	 * @param stream
	 * @return pdf representation of given stream
	 * @throws Exception
	 */
	public PDFDocument loadDocument(InputStream stream) throws UnsuportedHtmlException {
		return loadFile(stream);
	}

	private PDFDocument loadFile(InputStream stream) throws UnsuportedHtmlException {

		Element body;
		try {
			Document.OutputSettings outputSettings = new Document.OutputSettings();
			outputSettings.prettyPrint(false);
			// Parse local xhtml file
			Document html = Jsoup.parse(stream, null, "");
			html.outputSettings(outputSettings);
			// Retrieve body
			body = html.body();
		} catch (IOException e) {
			throw new UnsuportedHtmlException("Unable to parse html file.", e);
		}
		PDFDocument doc = new PDFDocument();
		try {
			// Start adding elements to pdf
			for (Node node : body.childNodes()) {
				PDFObject obj = getObjectFromNode(node);
				if (!(obj instanceof Empty)) {
					doc.addPDFObject(obj);
				}
			}
		} catch (Exception e) {
			try {
				doc.close();
			} catch (Exception e1) {
				log.warn("PDFDocument not closed properly.", e);
			}
			throw new UnsuportedHtmlException("Unable to parse html file.", e);
		}

		return doc;

	}

	/**
	 * Get a PDFObject from various basic html node
	 * 
	 * @param node
	 * @return
	 * @throws UnsuportedHtmlException
	 */
	private PDFObject getObjectFromNode(Node node) throws UnsuportedHtmlException {
		if ((node instanceof TextNode) && StringUtils.isNotBlank(((TextNode) node).text())) {
			return new PDFParagraph(((TextNode) node).text());
		} else if (node.nodeName().equals("p")) {
			return new PDFParagraph(getChildText(node));
		} else if (node.nodeName().equals("h1")) {
			return new PDFTitle(getChildText(node), PDFTitle.Type.H1);
		} else if (node.nodeName().equals("h2")) {
			return new PDFTitle(getChildText(node), PDFTitle.Type.H2);
		} else if (node.nodeName().equals("h3")) {
			return new PDFTitle(getChildText(node), PDFTitle.Type.H3);
		} else if (node.nodeName().equals("h4")) {
			return new PDFTitle(getChildText(node), PDFTitle.Type.H4);
		} else if (node.nodeName().equals("table")) {
			return getTableFromNode(node);
		}
		return new Empty();
	}

	/**
	 * Compute string text in current node <br>
	 * are replaced with \n <span> are replaced with five space characters to avoid
	 * issue with tabulation char
	 * 
	 * @param node
	 * @return text content in given node
	 */
	private String getChildText(Node node) {
		StringBuilder text = new StringBuilder();
		for (Node paragraphNode : node.childNodes()) {
			if (paragraphNode instanceof TextNode) {
				text.append(((TextNode) paragraphNode).text());
			} else if (paragraphNode.nodeName().equals("br")) {
				text.append("\n");
			} else if (paragraphNode.nodeName().equals("span")) {
				text.append("     ");
			}
		}
		return text.toString();
	}

	/**
	 * Get a Table instance from a html jsoup table node
	 * 
	 * @param tableNode
	 * @return Table instance from given html table
	 * @throws UnsuportedHtmlException
	 */
	private PDFTable getTableFromNode(Node tableNode) throws UnsuportedHtmlException {
		PDFTable table = new PDFTable();
		for (Node node : tableNode.childNodes()) {
			if (node.nodeName().equals("thead")) {

			} else if (node.nodeName().equals("tbody")) {
				table.getRows().addAll(getRowsFromTBodyNode(node));
			} else if (node.nodeName().equals("tfoot") || node.nodeName().equals("caption")) {

			} else if (!(node instanceof TextNode)) {
				throw new UnsuportedHtmlException(
						String.format("A table contains an unexpected tag.Please verify input html file. tagName : %s",
								node.nodeName()));
			}
		}
		return table;
	}

	/**
	 * 
	 * @return
	 * @throws UnsuportedHtmlException
	 */
	private List<FPDTr> getRowsFromTBodyNode(Node trNode) throws UnsuportedHtmlException {
		List<FPDTr> rows = new ArrayList<>();
		for (Node node : trNode.childNodes()) {
			if (node.nodeName().equals("tr")) {
				rows.add(getRowFromTRNode(node));
			} else if (!(node instanceof TextNode)) {
				throw new UnsuportedHtmlException(String.format(
						"A table body contains an unexpected tag.Please verify input html file. tagName : %s",
						node.nodeName()));
			}
		}
		return rows;
	}

	/**
	 * 
	 * @return
	 * @throws UnsuportedHtmlException
	 */
	private FPDTr getRowFromTRNode(Node trNode) throws UnsuportedHtmlException {
		FPDTr row = new FPDTr();
		for (Node node : trNode.childNodes()) {
			if (node.nodeName().equals("td")) {
				row.getCells().add(getCellFromTDNode(node));
			} else if (!(node instanceof TextNode)) {
				throw new UnsuportedHtmlException(String.format(
						"A table row contains an unexpected tag.Please verify input html file. tagName : %s",
						node.nodeName()));
			}
		}
		return row;
	}

	/**
	 * 
	 * @return
	 * @throws UnsuportedHtmlException
	 */
	private FPDTd getCellFromTDNode(Node tdNode) throws UnsuportedHtmlException {
		PDFObject obj;
		for (Node node : tdNode.childNodes()) {
			obj = getObjectFromNode(node);
			if (!(obj instanceof Empty)) {
				return new FPDTd(obj);
			}
		}
		return new FPDTd(getObjectFromNode(tdNode));
	}

}
