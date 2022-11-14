package com.vten.pdfgen.test.table;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.pdfobjects.Empty;
import com.vten.pdfgen.model.pdfobjects.FPDTd;
import com.vten.pdfgen.model.pdfobjects.FPDTr;
import com.vten.pdfgen.model.pdfobjects.PDFImage;
import com.vten.pdfgen.model.pdfobjects.PDFParagraph;
import com.vten.pdfgen.model.pdfobjects.PDFTable;

import lombok.extern.slf4j.Slf4j;

@Suite
@Slf4j
class TableTest {

	private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed      do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n Massa ultricies mi quis hendrerit dolor. Non quam lacus suspendisse faucibussssssssssssss interdum posuere lorem ipsum. Feugiat sed lectus vestibulum mattis ullamcorper velit sed. Vulputate odio ut enim blandit volutpat maecenas. Non diam phasellus vestibulum lorem sed risus ultricies tristique. Morbi quis commodo odio aenean sed adipiscing diam. Dignissim suspendisse in est ante. Ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue eget. Cursus metus aliquam eleifend mi in nulla. Pellentesque sit amet porttitor eget dolor morbi non. Magna fermentum iaculis eu non diam phasellus vestibulum lorem sed. Scelerisque viverra mauris in aliquam sem fringilla ut morbi tincidunt. In eu mi bibendum neque. Sodales ut etiam sit amet nisl purus in mollis.";

	@Test
	void testSimpleTable() {
		try (PDFDocument document = new PDFDocument()) {

			PDFTable t = new PDFTable();
			FPDTr r = new FPDTr();
			r.getCells().add(new FPDTd(new PDFParagraph("A " + TEXT.substring(250))));
			r.getCells().add(new FPDTd(new PDFParagraph("B " + TEXT.substring(250))));
			r.getCells().add(new FPDTd(new Empty()));
			t.getRows().add(r);

			document.addTable(t);

			Assertions.assertNotEquals(PDRectangle.A4.getHeight() /*- s.getMargin().getUp()*/,
					document.getHeightCursor());
		} catch (IOException e) {
			log.error("Error testSimpleTable", e);
			Assert.assertFalse(false);
		}
	}

	@Test
	void testTableMultiplePage() {
		try (PDFDocument document = new PDFDocument()) {

			PDFTable t = new PDFTable();
			FPDTr r = new FPDTr();
			FPDTd c1 = new FPDTd(new PDFParagraph("A " + TEXT.substring(250)));
			FPDTd c2 = new FPDTd(new PDFParagraph("B " + TEXT.substring(250)));
			r.getCells().add(c1);
			r.getCells().add(c2);
			t.getRows().add(r);
			FPDTr r2 = new FPDTr();
			FPDTd c21 = new FPDTd(new PDFParagraph("C" + " 2 ABCDA AAAAAAAAAAAAAAAAAA"));
			FPDTd c22 = new FPDTd(new PDFParagraph("D " + TEXT.substring(320)));
			FPDTd c23 = new FPDTd(
					new PDFParagraph("E Lorem ipsum dolor sit Lorem ipsum dolor sit Lorem ipsum dolor sit"));
			r2.getCells().add(c21);
			r2.getCells().add(c22);
			r2.getCells().add(c23);
			t.getRows().add(r2);

			FPDTr r2b = new FPDTr();
			FPDTd c2b1 = new FPDTd(new PDFParagraph("C" + " 2B ABCD"));
			FPDTd c2b2 = new FPDTd(new PDFParagraph("D " + TEXT.substring(320)));
			FPDTd c2b3 = new FPDTd(
					new PDFParagraph("E Lorem ipsum dolor sit Lorem ipsum dolor sit Lorem ipsum dolor sit"));
			r2b.getCells().add(c2b1);
			r2b.getCells().add(c2b2);
			r2b.getCells().add(c2b3);
			t.getRows().add(r2b);

			FPDTr r3 = new FPDTr();
			FPDTd c31 = new FPDTd(new PDFParagraph("C" + " ABC"));
			FPDTd c32 = new FPDTd(new PDFParagraph("D " + TEXT.substring(370)));
			FPDTd c33 = new FPDTd(new PDFImage("src/main/resources/Hello2.png", document));
			r3.getCells().add(c31);
			r3.getCells().add(c32);
			r3.getCells().add(c33);
			t.getRows().add(r3);

			FPDTr r4 = new FPDTr();
			FPDTd c41 = new FPDTd(new PDFParagraph("C" + TEXT.substring(450)));
			FPDTd c42 = new FPDTd(new PDFParagraph("D " + TEXT.substring(370)));
			FPDTd c43 = new FPDTd(
					new PDFParagraph("E Lorem ipsum dolor sit Lorem ipsum dolor sit Lorem ipsum dolor sit"));
			r4.getCells().add(c41);
			r4.getCells().add(c42);
			r4.getCells().add(c43);
			t.getRows().add(r4);

			document.addTable(t);
			Assertions.assertNotEquals(1, document.getPageCount());
		} catch (IOException e) {
			log.error("Error testTableMultiplePage", e);
			Assert.assertFalse(false);
		}
	}
}
