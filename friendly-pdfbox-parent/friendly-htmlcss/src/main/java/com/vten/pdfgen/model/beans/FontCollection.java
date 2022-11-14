package com.vten.pdfgen.model.beans;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import lombok.Data;

@Data
public class FontCollection {
	
	private String fontName = "Helvetica";
	private PDFont font = PDType1Font.HELVETICA;
	private PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
	private PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	private PDFont fontBoldItalic = PDType1Font.HELVETICA_BOLD_OBLIQUE;
}
