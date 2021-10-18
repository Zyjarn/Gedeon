package com.vten.gedeon.api.admin;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.model.property.PropertyType;

public interface PropertyTemplate extends PersistableObject{
	
	public default String getPropertyName() {
		return getProperties().get(GedeonProperties.PROP_NAME).getStringValue();
	}
	
	public default void setPropertyName(String propertyName) {
		setPropertyValue(GedeonProperties.PROP_NAME,propertyName);
	}
	
	public default PropertyType getType() {
		return PropertyType.valueOf(getProperties().get(GedeonProperties.PROP_PROPERTY_TYPE).getStringValue());
	}
	
	public default void setType(String type) {
		setPropertyValue(GedeonProperties.PROP_PROPERTY_TYPE,PropertyType.valueOf(type));
	}
	
	public default boolean isList() {
		return getProperties().get(GedeonProperties.PROP_IS_LIST).getBooleanValue();
	}
	
	public default void isList(Boolean val) {
		setPropertyValue(GedeonProperties.PROP_IS_LIST,val);
	}
	
	public default boolean isSystem() {
		return getProperties().get(GedeonProperties.PROP_IS_SYSTEM).getBooleanValue();
	}
	
	public default void setIsSystem(Boolean val) {
		setPropertyValue(GedeonProperties.PROP_IS_SYSTEM,val);
	}

}
