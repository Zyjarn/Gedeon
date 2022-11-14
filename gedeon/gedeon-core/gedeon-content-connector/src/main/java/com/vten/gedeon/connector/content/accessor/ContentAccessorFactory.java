package com.vten.gedeon.connector.content.accessor;

import org.springframework.lang.NonNull;

import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.connector.content.enums.StorageType;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonIOException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Factory to retrieve content accessor associated to given storage
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentAccessorFactory {

	/**
	 * Retrieve cached ContentAccessor for given storage
	 * 
	 * @param storage StorageArea defined in app
	 * @return ContentAccessor instance to manage content in given storage area
	 * @throws GedeonIOException if given storage is invalid
	 */
	public static GedeonContentAccessor getContentAccessor(@NonNull Storage storage) throws GedeonIOException {
		String storeType = storage.getStorageType();

		StorageType type = StorageType.parse(storeType);
		if (type == null) {
			throw new GedeonIOException(GedeonErrorCode.OE0120,
					String.format("type '%s' is unknow. How did you get that?", type));
		}
		GedeonContentAccessor accessor = null;
		switch (type) {
		case FILESYSTEM:
			accessor = new FileSystemContentAccessor();
			break;
		case FTP:
			accessor = new FTPContentAccessor();
			break;
		default:
			throw new GedeonIOException(GedeonErrorCode.OE0120,
					String.format("type '%s' is unknow. How did you get that?", type));
		}
		accessor.init(storage);
		return accessor;
	}
}