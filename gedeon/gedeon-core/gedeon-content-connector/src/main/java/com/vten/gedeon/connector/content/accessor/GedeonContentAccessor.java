package com.vten.gedeon.connector.content.accessor;

import java.io.InputStream;

import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.exception.GedeonIOException;

public interface GedeonContentAccessor {

	void init(Storage storage) throws GedeonIOException;

	InputStream get(String path) throws GedeonIOException;

	void add(String path, InputStream content) throws GedeonIOException;

	void delete(String path) throws GedeonIOException;

	void close() throws GedeonIOException;
}
