package com.vten.gedeon.api;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;

public interface GedObject {

	public Properties getProperties();

	public void setProperties(Properties properties);

	public void setPropertyValue(String propertyName, Object value);

	public default GedId getId() {
		// Force return of null value if null instead of exception thrown
		if (getProperties().get(GedeonProperties.PROP_ID) == null) {
			return null;
		}
		return getProperties().get(GedeonProperties.PROP_ID).getIdValue();
	}

	/**
	 * Set the id of the object TODO throw exception for existing object
	 * 
	 * @param id
	 */
	public default void setId(GedId id) {
		setPropertyValue(GedeonProperties.PROP_ID, id);
	}

	public String getClassName();

	public default String getName() {
		return getProperties().get(GedeonProperties.PROP_NAME).getStringValue();
	}

	public default void setName(String name) {
		setPropertyValue(GedeonProperties.PROP_NAME, name);
	}

	/**
	 * Return the class definition of the current object
	 * 
	 * @return class definition
	 */
	public ClassDefinition getClassDefinition();

	/**
	 * Return the class definition of the current object
	 * 
	 * @return class definition
	 */
	public void setClassDefinition(ClassDefinition classDefinition);

	/**
	 * Get the collection associated to the object
	 * 
	 * @return
	 */
	public GedeonCollection getGedeonCollection();

	/**
	 * Set the collection associated to the object
	 * 
	 * @return
	 */
	public void setGedeonCollection(GedeonCollection collection);
}
