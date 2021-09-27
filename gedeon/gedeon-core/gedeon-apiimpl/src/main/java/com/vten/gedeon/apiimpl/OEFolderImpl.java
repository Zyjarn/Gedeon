package com.vten.gedeon.apiimpl;

import com.vten.gedeon.api.OEFolder;

public class OEFolderImpl extends ContainerImpl implements OEFolder{

	@Override
	public String getClassName() {
		return OEFolder.class.getSimpleName();
	}

}
