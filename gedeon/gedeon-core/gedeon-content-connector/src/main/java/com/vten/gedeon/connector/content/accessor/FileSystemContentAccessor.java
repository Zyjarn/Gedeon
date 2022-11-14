package com.vten.gedeon.connector.content.accessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.StringUtils;

import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonIOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemContentAccessor implements GedeonContentAccessor {

	private static final String BASENAME_FORMAT = "//%s/%s/Gedeon/content/";

	private String filePrefix;

	@Override
	public void init(Storage storage) throws GedeonIOException {
		String host = storage.getStorageHost();
		String basename = storage.getStorageBasename();
		if (StringUtils.isBlank(host) || host.startsWith("/")) {
			throw new GedeonIOException(GedeonErrorCode.OE0120, "host is null or invalid.");
		}
		if (StringUtils.isBlank(basename)) {
			throw new GedeonIOException(GedeonErrorCode.OE0120, "basename can't be null.");
		}
		if (basename.contains(":")) {
			basename = basename.replace(":", "$");
		}
		if (basename.endsWith("/")) {
			basename = StringUtils.left(basename, basename.length() - 1);
		}

		filePrefix = String.format(BASENAME_FORMAT, host, basename);
		File dir = new File(filePrefix);
		try {
			Files.createDirectories(dir.toPath());
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0120, "Issue while accessing storage.");
		}
	}

	@Override
	public InputStream get(String path) throws GedeonIOException {
		try {
			File fileContent = new File(filePrefix.concat(path));
			if (!fileContent.exists()) {
				throw new IOException("Missing file");
			}
			return new FileInputStream(fileContent);
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0113, e, path);
		}
	}

	@Override
	public void add(String path, InputStream content) throws GedeonIOException {
		try {
			Files.copy(content, Path.of(filePrefix, path), StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0114, e, path);
		}
	}

	@Override
	public void delete(String path) throws GedeonIOException {
		try {
			File fileContent = new File(filePrefix.concat(path));
			if (!fileContent.exists()) {
				log.warn("Request deletion on already missing file.");
			} else {
				Files.delete(fileContent.toPath());
			}
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0113, e, path);
		}

	}

	@Override
	public void close() throws GedeonIOException {
		// Nothing to do
	}

}
