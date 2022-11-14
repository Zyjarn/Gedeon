package com.vten.pdfgen.service;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import com.vten.pdfgen.exception.UnsuportedHtmlException;
import com.vten.pdfgen.model.PDFDocument;

import lombok.extern.slf4j.Slf4j;

@Suite
@Slf4j
class HtmlToPdfServiceTest {

	@Test
	void testBasicXML() {
		HtmlToPdfService service = new HtmlToPdfService();
		try (PDFDocument doc = service.loadDocument("./src/test/resources/html/testOK.xml")) {
			doc.saveAs("output3.pdf");
			Assertions.assertNotEquals(0, doc.getCurrentPageIndex());
		} catch (IOException | UnsuportedHtmlException e) {
			log.error("Error testBasicXML", e);
		}
	}

//	@Test
//	void testUnvalidXML() throws IOException, Exception {
//		HtmlToPdfService service = new HtmlToPdfService();
//		try (PDFDocument doc = service.loadDocument("./src/test/resources/html/testKO.xml")) {
//			Assertions.assertFalse(true);
//		} catch (IOException | UnsuportedHtmlException e) {
//			log.debug("trace valid error testUnvalidXML", e);
//			Assertions.assertTrue(e instanceof UnsuportedHtmlException);
//		}
//	}
//
//	@Test
//	void testUnvalidXML_NotTrTag() throws IOException, Exception {
//		HtmlToPdfService service = new HtmlToPdfService();
//		try (PDFDocument doc = service.loadDocument("./src/test/resources/html/testKOtableTR.xml")) {
//			Assertions.assertFalse(true);
//		} catch (IOException | UnsuportedHtmlException e) {
//			log.debug("trace valid error testUnvalidXML_NotTrTag", e);
//			Assertions.assertTrue(e.getMessage().startsWith("A table body"));
//		}
//	}
//
//	@Test
//	void testUnvalidXML_NotTdTag() throws IOException, Exception {
//		HtmlToPdfService service = new HtmlToPdfService();
//		try (PDFDocument doc = service.loadDocument("./src/test/resources/html/testKO.xml")) {
//			Assertions.assertFalse(true);
//		} catch (IOException | UnsuportedHtmlException e) {
//			log.debug("trace valid error testUnvalidXML_NotTdTag", e);
//			Assertions.assertTrue(e.getMessage().startsWith("A table row"));
//		}
//	}
}
