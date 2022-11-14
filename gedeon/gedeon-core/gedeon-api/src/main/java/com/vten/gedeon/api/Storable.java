package com.vten.gedeon.api;

import java.io.InputStream;

import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.exception.GedeonIOException;

public interface Storable extends AbstractPersistableObject {

	public default Storage getStorage() {
		return (Storage) getProperties().get(GedeonProperties.PROP_STORAGE).getObjectValue();
	}

	public default String getMimeType() {
		return getProperties().get(GedeonProperties.PROP_MIME_TYPE).getStringValue();
	}

	public default String getContentName() {
		return getProperties().get(GedeonProperties.PROP_CONTENT_NAME).getStringValue();
	}

	public default long getStoredContentSize() {
		return getProperties().get(GedeonProperties.PROP_STORED_CONTENT_SIZE).getIntegerValue();
	}

	public default InputStream getContent() throws GedeonIOException {
		return getGedeonCollection().getContent(this);
	}

}
