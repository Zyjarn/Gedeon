package com.vten.gedeon.api;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.api.utils.OEId;

public interface GedObject {
	
	public Properties getProperties();
	public void setProperties(Properties properties);
	
	public default OEId getId() {
		return getProperties().get(GedeonProperties.PROP_ID).getIdValue();
	}
	
	/**
	 * Set the id of the object 
	 * TODO throw exception for existing object
	 * @param id
	 */
	public default void setId(OEId id) {
		getProperties().get(GedeonProperties.PROP_ID).setObjectValue(id);
	}
	
	public String getClassName();
		
	public default String getName() {
		return getProperties().get(GedeonProperties.PROP_NAME).getStringValue();
	}
	
	public default void setName(String name) {
		getProperties().get(GedeonProperties.PROP_NAME).setObjectValue(name);
	}
	
	/**
	 * Return the class definition of the current object
	 * @return class definition
	 */
	public ClassDefinition getClassDefinition();
}
