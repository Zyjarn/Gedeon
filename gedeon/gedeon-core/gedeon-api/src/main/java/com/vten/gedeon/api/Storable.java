package com.vten.gedeon.api;

import com.vten.gedeon.api.utils.GedeonProperties;

public interface Storable extends AbstractPersistableObject{

	public default String getMimeType() {
		return getProperties().get(GedeonProperties.PROP_MIME_TYPE).getStringValue();
	}
	
	public default String getContentName() {
		return getProperties().get(GedeonProperties.PROP_CONTENT_NAME).getStringValue();
	}
	
	public default long getStoredContentSize() {
		return getProperties().get(GedeonProperties.PROP_STORED_CONTENT_SIZE).getIntegerValue();
	}
}
