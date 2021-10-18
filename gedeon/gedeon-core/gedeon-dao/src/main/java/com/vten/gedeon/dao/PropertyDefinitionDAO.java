package com.vten.gedeon.dao;

import java.util.List;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.utils.SaveMode;

public interface PropertyDefinitionDAO {
	
	public PropertyDefinition getObject(GedId id);
	
	public List<PropertyDefinition> getPropertiesDefinitionForClass(ClassDefinition classDef);
	
	public void saveObject(PropertyDefinition obj, SaveMode mode);
	
	public void deleteObject(PropertyDefinition obj);
	
	public PropertyDefinition newCopyInstance(PropertyDefinition obj);
}
