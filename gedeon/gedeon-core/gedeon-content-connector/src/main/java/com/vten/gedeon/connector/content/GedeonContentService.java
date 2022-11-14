package com.vten.gedeon.connector.content;

import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.connector.content.accessor.ContentAccessorFactory;
import com.vten.gedeon.connector.content.accessor.GedeonContentAccessor;
import com.vten.gedeon.exception.GedeonIOException;
import com.vten.gedeon.security.file.GedeonEncryptionService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class GedeonContentService {

	private static final String FOLDER_TREE = "%s/%s/%s/%s";

	@Autowired
	private GedeonEncryptionService contentEncryptor;

	public void initStorage(Storage storage) throws GedeonIOException {
		GedeonContentAccessor contentAccessor = ContentAccessorFactory.getContentAccessor(storage);
		contentAccessor.init(storage);
	}

	public InputStream getContent(GedId contentId, Storage storage) throws GedeonIOException {

		GedeonContentAccessor contentAccessor = ContentAccessorFactory.getContentAccessor(storage);

		if (BooleanUtils.isTrue(storage.getIsStorageEncrypt())) {
			return contentEncryptor.decodeInputStream(contentAccessor.get(decodePath(contentId)));
		} else {
			return contentAccessor.get(decodePath(contentId));
		}
	}

	public void addContent(GedId contentId, Storage storage, InputStream contentStream) throws GedeonIOException {

		GedeonContentAccessor contentAccessor = ContentAccessorFactory.getContentAccessor(storage);

		if (BooleanUtils.isTrue(storage.getIsStorageEncrypt())) {
			contentAccessor.add(decodePath(contentId), contentEncryptor.decodeInputStream(contentStream));
		} else {
			contentAccessor.add(decodePath(contentId), contentStream);
		}
	}

	private String decodePath(@NonNull GedId contentId) {
		String id = contentId.toString();
		return String.format(FOLDER_TREE, Arrays.toString(Arrays.copyOfRange(id.split("(?<=\\G.{2})"), 0, 3)), id);
	}

}
