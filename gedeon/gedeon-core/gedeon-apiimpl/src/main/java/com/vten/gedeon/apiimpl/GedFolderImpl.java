package com.vten.gedeon.apiimpl;

import com.vten.gedeon.api.GedFolder;

public class GedFolderImpl extends ContainerImpl implements GedFolder{

	@Override
	public String getClassName() {
		return GedFolder.class.getSimpleName();
	}

}
