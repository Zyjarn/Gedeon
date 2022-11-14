package com.vten.pdfgen.service.css;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import com.vten.pdfgen.exception.CSSParserException;
import com.vten.pdfgen.model.beans.css.CSSStyleSheet;
import com.vten.pdfgen.service.css.util.CSSParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSSParserService {

	public CSSStyleSheet parseFile(String filename) throws IOException, CSSParserException {
		try (InputStream fis = new FileInputStream(new File(filename));
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));) {
			return parseReader(reader);
		}
	}

	public CSSStyleSheet parseStream(InputStream stream) throws IOException, CSSParserException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			return parseReader(reader);
		}
	}

	public CSSStyleSheet parseString(String content) throws IOException, CSSParserException {
		try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
			return parseReader(reader);
		}
	}

	public CSSStyleSheet parseReader(BufferedReader reader) throws IOException, CSSParserException {
		long start = System.currentTimeMillis();
		log.debug("start");
		CSSParser parser = new CSSParser();
		int lineNb = 1;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				parser.feed(line);
				lineNb++;
			}
		} catch (CSSParserException e) {
			log.error("Issue while parsing css, line={}, error={}", lineNb, e.getMessage());
			throw e;
		}
		log.debug("end, time={}", (System.currentTimeMillis() - start));
		return parser.getStyleSheet();
	}

}
