package com.vten.gedeon.apiimpl;

import com.vten.gedeon.api.GedDocument;
import com.vten.gedeon.api.utils.GedeonProperties;

public class GedDocumentImpl extends AbstractDocumentImpl implements GedDocument{

	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_GEDDOCUMENT;
	}
}
