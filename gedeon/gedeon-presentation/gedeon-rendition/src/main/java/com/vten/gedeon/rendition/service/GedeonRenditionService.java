package com.vten.gedeon.rendition.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
public class GedeonRenditionService {

	public void addDocToRendition(String filename, InputStream stream) {
		Tika tika = new Tika();
		String mimetype = "plain/text";
		try {
			mimetype = tika.detect(stream, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (mimetype.equals("plain/text")) {

		}
	}
}
