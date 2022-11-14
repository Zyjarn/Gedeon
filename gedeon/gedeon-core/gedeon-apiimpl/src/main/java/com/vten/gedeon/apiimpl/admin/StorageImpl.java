package com.vten.gedeon.apiimpl.admin;

import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;
import com.vten.gedeon.utils.SaveMode;

public class StorageImpl extends PersistableObjectImpl implements Storage {

	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_STORAGE;
	}

	@Override
	public void save(SaveMode mode) {
		getGedeonCollection().saveStorage(this, mode);
	}
}
