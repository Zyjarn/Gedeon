package com.vten.pdfgen.model.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quartet {

	public static final Quartet DEFAULT = new Quartet(0, 0, 0, 0);
	public static final Quartet ALL_25 = new Quartet(25, 25, 25, 25);

	private float left;
	private float up;
	private float right;
	private float down;
}
