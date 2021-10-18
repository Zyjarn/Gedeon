package com.vten.gedeon.dao;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.utils.SaveMode;

public interface GedCollectionDAO {
	
	public GedeonCollection getObject(GedId id);
	
	public GedeonCollection getObject(String name);
	
	public void createCollection(GedeonCollection obj);
	
	public void saveObject(GedeonCollection obj, SaveMode mode);
	
	public void deleteObject(GedeonCollection obj);
	
}
