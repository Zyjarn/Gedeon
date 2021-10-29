package com.vten.gedeon.dao;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.utils.SaveMode;

public interface ClassDefinitionDAO {
	
	public ClassDefinition getObject(GedeonCollection collection,GedId id);
	
	public ClassDefinition getObject(GedeonCollection collection,String name);
	
	public void saveObject(ClassDefinition obj, SaveMode mode);
	
	public void deleteObject(ClassDefinition obj);
	
}
