package com.vten.gedeon.dao;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.utils.SaveMode;

public interface ClassDefinitionDAO {
	
	public ClassDefinition getObject(String className, String id);
	
	public void saveObject(ClassDefinition obj, SaveMode mode);
	
	public void deleteObject(ClassDefinition obj);
	
	public ClassDefinition getInstanceByClassName(String className);
}
