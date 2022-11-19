package com.vten.gedeon.dao;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.admin.GedeonClassDefinition;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.utils.SaveMode;

public interface ClassDefinitionDAO {
	
	public GedeonClassDefinition getObject(GedeonCollection collection,GedId id);
	
	public GedeonClassDefinition getObject(GedeonCollection collection,String name);
	
	public void saveObject(GedeonClassDefinition obj, SaveMode mode);
	
	public void deleteObject(GedeonClassDefinition obj);
	
}
