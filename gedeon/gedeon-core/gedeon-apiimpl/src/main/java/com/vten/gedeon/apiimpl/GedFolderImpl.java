package com.vten.gedeon.apiimpl;

import com.vten.gedeon.api.GedFolder;
import com.vten.gedeon.api.utils.GedeonProperties;

public class GedFolderImpl extends ContainerImpl implements GedFolder{

	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_GEDFOLDER;
	}
}
