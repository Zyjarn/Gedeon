package com.vten.pdfgen.test.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import com.vten.pdfgen.exception.CSSParserException;
import com.vten.pdfgen.model.beans.css.CSSRules;
import com.vten.pdfgen.model.beans.css.CSSStyleSheet;
import com.vten.pdfgen.model.beans.html.HtmlElement;
import com.vten.pdfgen.model.beans.html.impl.Div;
import com.vten.pdfgen.model.beans.html.impl.Image;
import com.vten.pdfgen.model.beans.html.impl.Td;
import com.vten.pdfgen.service.css.CSSParserService;

import lombok.extern.slf4j.Slf4j;

@Suite
@Slf4j
class PDFStyleTest {

	private CSSParserService css = new CSSParserService();

	@Test
	void selectorClassNameUnique() throws IOException, CSSParserException {
		CSSStyleSheet style = css.parseString(".test{colspan:5}");
		CSSRules rules;
		// Must match
		HtmlElement o1 = new Div();
		o1.getHtmlClass().add("test");
		rules = style.getStyle(o1);
		Assertions.assertEquals(5, rules.getColspan().getValue());

		// Must match
		HtmlElement o2 = new Div();
		o2.getHtmlClass().add("test1");
		o2.getHtmlClass().add("test");
		rules = style.getStyle(o2);
		Assertions.assertEquals(5, rules.getColspan().getValue());

		// Must not match
		HtmlElement o3 = new Div();
		o3.getHtmlClass().add("test2");
		o3.getHtmlClass().add("test1");
		rules = style.getStyle(o3);
		Assertions.assertEquals(1, rules.getColspan().getValue());

	}

	@Test
	void selectorClassNameMultiple() {

		try {
			CSSStyleSheet style = css.parseString(".test.abc{colspan:5}");

			CSSRules rules;
			// Must match
			HtmlElement o1 = new Div();
			o1.getHtmlClass().addAll(Arrays.asList("test", "abc"));
			rules = style.getStyle(o1);
			Assertions.assertEquals(5, rules.getColspan().getValue());

			// Must match
			HtmlElement o2 = new Div();
			o2.getHtmlClass().addAll(Arrays.asList("def", "test", "abc", "ghi"));
			rules = style.getStyle(o2);
			Assertions.assertEquals(5, rules.getColspan().getValue());

			// Must not match
			HtmlElement o3 = new Div();
			o3.getHtmlClass().addAll(Arrays.asList("test1", "test2"));
			rules = style.getStyle(o3);
			Assertions.assertEquals(1, rules.getColspan().getValue());

			// Must not match
			HtmlElement o4 = new Div();
			o4.getHtmlClass().add("test");
			rules = style.getStyle(o4);
			Assertions.assertEquals(1, rules.getColspan().getValue());

		} catch (IOException | CSSParserException e) {
			log.error("Error selectorClassNameMultiple", e);
		}
	}

	@Test
	void selectorElement() throws IOException, CSSParserException {

		CSSStyleSheet style = css.parseString("img{colspan:5} td{colspan:6} h1{colspan:7}");
		CSSRules rules;

		// Must match
		HtmlElement o1 = new Image();
		rules = style.getStyle(o1);
		Assertions.assertEquals(5, rules.getColspan().getValue());

		// Must match
		HtmlElement o2 = new Td();
		rules = style.getStyle(o2);
		Assertions.assertEquals(6, rules.getColspan().getValue());

		// Must not match any rules
		HtmlElement o3 = new Div();
		rules = style.getStyle(o3);
		Assertions.assertEquals(1, rules.getColspan().getValue());

	}

	@Test
	void selectorElementAndClass() throws IOException, CSSParserException {
		CSSStyleSheet style = css.parseString("img.test{colspan:5} td.test.test1{colspan:6} h1.test2{colspan:7}");
		CSSRules rules;

		// Must match
		HtmlElement o1 = new Image();
		o1.getHtmlClass().add("test");
		rules = style.getStyle(o1);
		Assertions.assertEquals(5, rules.getColspan().getValue());

		// Must match
		HtmlElement o2 = new Td();
		o2.getHtmlClass().addAll(Arrays.asList("test", "test1"));
		rules = style.getStyle(o2);
		Assertions.assertEquals(6, rules.getColspan().getValue());

		// Must not match any rules
		HtmlElement o3 = new Div();
		o3.getHtmlClass().addAll(Arrays.asList("test", "test1"));
		rules = style.getStyle(o3);
		Assertions.assertEquals(1, rules.getColspan().getValue());

	}

	@Test
	void testCSSSelectorOK() {
		List<String> list = Arrays.asList("#id", "div", "div,table", "div,table,td", "div[a=v]", "div,td[a=v]",
				"div[a=v],div[a=v]", "div[a=v],div", "div,div[a=v],div[a=v]", ".test", ".test.test1", ".test#id",
				"#id.test", ".test#id.test1", ".test.test1#id", ".test[a=v][b]", "div[a][b=v][c*=v]",
				"div.test#id.test1", "div[a].test#id.test1[a]", "div div[a].test#id.test1[a]");
		String style = "%s{colspan:5}";
		String test;
		for (int i = 0; i < list.size(); i++) {
			test = String.format(style, list.get(i));
			log.trace("testNb={}, value=\"{}\"", i, test);
			try {

				css.parseString(test);

				Assertions.assertFalse(false);
			} catch (IOException | CSSParserException e) {
				log.error(String.format("Error in testCSSSelectorOK : %s", test), e);
				Assertions.assertFalse(true);
			}
		}

	}

	@Test
	void testCSSSelectorKO() {

		List<String> list = Arrays.asList("#", ".", ",", "div,", "div,[a=v]", ",td[a=v]", "[a=v]", "div[a=v,div",
				"diva=v]");

		String style = "%s{colspan:5}";
		String test;
		for (int i = 0; i < list.size(); i++) {
			test = String.format(style, list.get(i));
			log.debug("testNb={}, value=\"{}\"", i, test);
			try {
				CSSStyleSheet stylesheet = css.parseString(test);
				// Must no go here
				log.debug("Error in testCSSSelectorKO");
				Assertions.assertNotNull(stylesheet);
			} catch (Exception e) {
				log.trace("Exception trace", e);
				Assertions.assertTrue(e instanceof CSSParserException);

			}
		}

	}
}
