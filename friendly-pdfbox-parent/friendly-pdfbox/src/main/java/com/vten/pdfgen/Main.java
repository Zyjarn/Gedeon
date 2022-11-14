package com.vten.pdfgen;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vten.pdfgen.model.PDFDocument;
import com.vten.pdfgen.model.pdfobjects.FPDTd;
import com.vten.pdfgen.model.pdfobjects.PDFImage;
import com.vten.pdfgen.model.pdfobjects.PDFParagraph;
import com.vten.pdfgen.model.pdfobjects.FPDTr;
import com.vten.pdfgen.model.pdfobjects.PDFTable;
import com.vten.pdfgen.model.pdfobjects.PDFTitle;
import com.vten.pdfgen.service.HtmlToPdfService;

//@SpringBootApplication
//@EnableAutoConfiguration
public class Main {

	private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed      do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n Massa ultricies mi quis hendrerit dolor. Non quam lacus suspendisse faucibussssssssssssss interdum posuere lorem ipsum. Feugiat sed lectus vestibulum mattis ullamcorper velit sed. Vulputate odio ut enim blandit volutpat maecenas. Non diam phasellus vestibulum lorem sed risus ultricies tristique. Morbi quis commodo odio aenean sed adipiscing diam. Dignissim suspendisse in est ante. Ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue eget. Cursus metus aliquam eleifend mi in nulla. Pellentesque sit amet porttitor eget dolor morbi non. Magna fermentum iaculis eu non diam phasellus vestibulum lorem sed. Scelerisque viverra mauris in aliquam sem fringilla ut morbi tincidunt. In eu mi bibendum neque. Sodales ut etiam sit amet nisl purus in mollis.";
	private static final String TEXT2 = "Habitasse platea dictumst quisque sagittis purus sit amet volutpat. Sed vulputate odio ut enim blandit volutpat maecenas. Proin libero nunc consequat interdum varius. Viverra aliquet eget sit amet tellus cras adipiscing. Diam donec adipiscing tristique risus nec feugiat in fermentum posuere. Placerat vestibulum lectus mauris ultrices eros in cursus turpis massa. A erat nam at lectus urna duis convallis convallis. Lorem ipsum dolor sit amet consectetur adipiscing elit duis. Neque laoreet suspendisse interdum consectetur libero. Mattis molestie a iaculis at erat pellentesque. Ullamcorper morbi tincidunt ornare massa eget egestas purus viverra accumsan. Nisi quis eleifend quam adipiscing vitae proin sagittis. Egestas purus viverra accumsan in nisl nisi scelerisque eu ultrices. Massa enim nec dui nunc mattis. Purus sit amet luctus venenatis lectus magna fringilla urna porttitor. Praesent tristique magna sit amet purus gravida quis. Bibendum at varius vel pharetra. Felis eget nunc lobortis mattis aliquam faucibus purus.";
	private static final String TEXT3 = "Enim blandit volutpat maecenas volutpat blandit aliquam etiam erat. Sed risus pretium quam vulputate dignissim suspendisse in. Dignissim convallis aenean et tortor at risus. Vulputate odio ut enim blandit volutpat. Tincidunt nunc pulvinar sapien et ligula ullamcorper malesuada proin. Quam nulla porttitor massa id neque aliquam vestibulum morbi. Quis blandit turpis cursus in hac. Sit amet cursus sit amet dictum. Quam quisque id diam vel quam elementum pulvinar. Convallis convallis tellus id interdum velit laoreet id donec ultrices. Convallis a cras semper auctor. Tempus urna et pharetra pharetra massa massa ultricies. Ullamcorper malesuada proin libero nunc consequat.";
	private static final String TEXT4 = "POHabitasse platea dictumst quisque sagittis purus sit amet volutpat. Sed vulputate odio ut enim blandit volutpat maecenas. Proin libero nunc consequat interdum varius. Viverra aliquet eget sit amet tellus cras adipiscing. Diam donec adipiscing tristique risus nec feugiat in fermentum posuere. Placerat vestibulum lectus mauris ultrices eros in cursus turpis massa. A erat nam at lectus urna duis convallis convallis. Lorem ipsum dolor sit amet consectetur adipiscing elit duis. Neque laoreet suspendisse interdum consectetur libero. Mattis molestie a iaculis at erat pellentesque. Ullamcorper morbi tincidunt ornare massa eget egestas purus viverra accumsan. Nisi quis eleifend quam adipiscing vitae proin sagittis. Egestas purus viverra accumsan in nisl nisi scelerisque eu ultrices. Massa enim nec dui nunc mattis. Purus sit amet luctus venenatis lectus magna fringilla urna porttitor. Praesent tristique magna sit amet purus gravida quis. Bibendum at varius vel pharetra. Felis eget nunc lobortis mattis aliquam faucibus purus.";
	private static final String A = "AAAAAA AAA AAAAAAAA AAA AA AAAAAA AAAAA AAAAAAA AAA AAA AAA AAAAAAAA AAAAAAAAAAAAA AAA AAAAAAAA AAA AA AAAAAA AAAAA AAAAAAA AAA AAA AAA AAAAAAAA AAAAAAA AAAAAA AAA AAAAAAAA AAA AA AAAAAA AAAAA AAAAAAA AAA AAA AAA AAAAAAAA AAAAAAA A***";

	public static void main(String[] args) throws Exception {

		// System.out.println(Character.isDigit(0));

		StringBuilder sb = new StringBuilder();
		System.out.println(sb.toString().isBlank());
		List a = Arrays.asList("A", "B", "C", "D");
		System.out.println(a.subList(0, 0));

		try (PDFDocument document = new PDFDocument()) {
			document.addTitle("Jesus sur les flôts", PDFTitle.Type.H1);
			document.addParagraph(TEXT + TEXT2 + TEXT3 + TEXT + TEXT3);

			document.addParagraph("AAAAAAAAAAAAAAAAAAAAAA BBBBB CCC");

//			document.addParagraph(TEXT2);
//			
//			document.addParagraph(TEXT3);
//			
			// document.addParagraph(TEXT);
//			
			// document.addParagraph(A+A+A+A);

			document.addPage();
			document.addParagraph("Hello");
			document.addParagraph(TEXT);
			document.addTitle("Jesus sur les flôts", PDFTitle.Type.H1);
			document.addTitle("Chapitre premier", PDFTitle.Type.H2);
			document.addTitle("Verset 1", PDFTitle.Type.H3);
			document.addTitle("Mot 1", PDFTitle.Type.H4);
//			document.addImage("src/main/resources/Hello3.png");
//			
//			document.addParagraph("AAAAAAAAAAAAAAAAAAAAAA BBBBB CCC");
//			document.addParagraph(TEXT);
			// document.addParagraph(TEXT4);

			document.addImage("src/main/resources/Hello.png");

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

			document.saveAs("output2.pdf");

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		HtmlToPdfService service = new HtmlToPdfService();
		try (PDFDocument doc = service.loadDocument("./src/main/resources/test.xml")) {
			doc.saveAs("output3.pdf");
		}
		String selectorStr = "div";
		int start = 0;
		int end = 0;
		int add = 0;
		System.out.println(selectorStr);
		for (String s : Arrays.asList(selectorStr.split("#={0}|\\."))) {
			if (!s.isBlank()) {
				end += s.length() + add;
				System.out.println(selectorStr.substring(start, end));
			}
			start = end;
			add = 1;
		}

		System.out.println(";;;;;;;;;;;;;;;;;;");
		Pattern p = Pattern.compile("([\\*~$^\\|]){0,1}=");
		Matcher m = p.matcher("a=v");

		System.out.println(m.find() + " " + m.group());
		/*
		 * try (PDPageContentStream stream = new
		 * PDPageContentStream(document.getDocument(), document.getPage(),
		 * AppendMode.APPEND, true)) { stream.setStrokingColor(Color.MAGENTA);
		 * stream.addRect(mediabox.getLowerLeftX(), mediabox.getLowerLeftY(),
		 * mediabox.getWidth(), mediabox.getHeight()); stream.stroke(); }
		 */
	}

}
