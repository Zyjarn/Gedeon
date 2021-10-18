package com.vten.gedeon.dao;

import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.utils.SaveMode;

public interface PropertyTemplateDAO {

	/**
	 * Retrieve an instance of PropertyTemplate by its Id
	 * @param id of the object
	 * @return full instance of property template with the given id
	 * @throw GedeonRuntimeException if the object can't be retrieve
	 */
	public PropertyTemplate getObject(GedId id);
	
	/**
	 * Retrieve an instance of PropertyTemplate by its name
	 * @param name of the object
	 * @return full instance of property template with the given name
	 * @throw GedeonRuntimeException if the object can't be retrieve
	 */
	public PropertyTemplate getObject(String name);
	
	public void saveObject(PropertyTemplate obj, SaveMode mode);
	
	public void deleteObject(PropertyTemplate obj);
}
