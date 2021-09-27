package com.vten.gedeon.api.admin;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.PropertiesDefinition;
import com.vten.gedeon.api.property.PropertyDefinition;

public interface ClassDefinition extends PersistableObject{
	
	public PropertiesDefinition getPropertiesDefinitions();
	public void addPropertyDefinition(PropertyDefinition prop);
	
}
