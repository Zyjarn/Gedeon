package com.vten.gedeon.api.admin;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.utils.GedeonProperties;

public interface Storage extends PersistableObject {

	public default String getStorageType() {
		return getProperties().get(GedeonProperties.PROP_STORAGE_TYPE).getStringValue();
	}

	public default void setStorageType(String storageType) {
		setPropertyValue(GedeonProperties.PROP_STORAGE_TYPE, storageType);
	}

	public default String getStorageHost() {
		return getProperties().get(GedeonProperties.PROP_STORAGE_HOST).getStringValue();
	}

	public default void setStorageHost(String storageHost) {
		setPropertyValue(GedeonProperties.PROP_STORAGE_HOST, storageHost);
	}

	public default String getStorageUsername() {
		return getProperties().get(GedeonProperties.PROP_STORAGE_USERNAME).getStringValue();
	}

	public default void setStorageUsername(String storageUsername) {
		setPropertyValue(GedeonProperties.PROP_STORAGE_USERNAME, storageUsername);
	}

	public default String getStorageCredential() {
		return getProperties().get(GedeonProperties.PROP_STORAGE_CREDENTIAL).getStringValue();
	}

	public default void setStorageCredential(String storageCredential) {
		setPropertyValue(GedeonProperties.PROP_STORAGE_CREDENTIAL, storageCredential);
	}

	public default String getStorageBasename() {
		return getProperties().get(GedeonProperties.PROP_STORAGE_BASENAME).getStringValue();
	}

	public default void setStorageBasename(String storageBasename) {
		setPropertyValue(GedeonProperties.PROP_STORAGE_BASENAME, storageBasename);
	}

	public default Boolean getIsStorageEncrypt() {
		return getProperties().get(GedeonProperties.PROP_STORAGE_IS_ENCRYPTED).getBooleanValue();
	}

	public default void setIsStorageEncrypt(Boolean isStorageEncrypt) {
		setPropertyValue(GedeonProperties.PROP_STORAGE_IS_ENCRYPTED, isStorageEncrypt);
	}

	public default Boolean getIsStorageDefault() {
		return getProperties().get(GedeonProperties.PROP_STORAGE_IS_DEFAULT).getBooleanValue();
	}

	public default void setIsStorageDefault(Boolean isStorageDefault) {
		setPropertyValue(GedeonProperties.PROP_STORAGE_IS_DEFAULT, isStorageDefault);
	}

}
