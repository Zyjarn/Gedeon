package com.vten.pdfgen.model.beans.html;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HtmlAttribute {
	@NonNull
	private String name = StringUtils.EMPTY;
	@NonNull
	private String value = StringUtils.EMPTY;
}
