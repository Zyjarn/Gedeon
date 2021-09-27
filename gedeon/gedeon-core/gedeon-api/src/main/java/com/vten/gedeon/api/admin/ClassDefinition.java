package com.vten.gedeon.api.admin;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.PropertiesDefinition;
import com.vten.gedeon.api.property.PropertyDefinition;
import com.vten.gedeon.api.utils.GedeonProperties;

public interface ClassDefinition extends PersistableObject{
	
	public default boolean isAbstract() {
		return getProperties().get(GedeonProperties.PROP_IS_ABSTRACT).getBooleanValue();
	}
	
	public PropertiesDefinition getPropertiesDefinitions();
	public void addPropertyDefinition(PropertyDefinition prop);
	
}
