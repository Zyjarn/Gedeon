package com.vten.gedeon.api.admin;

import java.util.List;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;

public interface GedeonDomain extends PersistableObject{
	
	public List<GedeonCollection> getCollections();
	
}
