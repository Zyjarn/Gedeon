package com.vten.gedeon.api.property;

import com.vten.gedeon.api.GedObject;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.model.property.PropertyType;

public interface PropertyDefinition extends GedObject{
	
	public default String getPropertyName() {
		return getPropertyTemplate().getProperties().get(GedeonProperties.PROP_PROPERTY_NAME).getStringValue();
	}
	
	public default PropertyType getType() {
		return PropertyType.valueOf(getPropertyTemplate().getProperties().get(GedeonProperties.PROP_PROPERTY_TYPE).getStringValue());
	}
	
	public default boolean isList() {
		return getPropertyTemplate().getProperties().get(GedeonProperties.PROP_PROPERTY_IS_LIST).getBooleanValue();
	}
	
	public default boolean isRequired() {
		return getProperties().get(GedeonProperties.PROP_PROPERTY_IS_REQUIRED).getBooleanValue();
	}
	
	public default void isRequired(boolean val) {
		getProperties().get(GedeonProperties.PROP_PROPERTY_IS_REQUIRED).setObjectValue(val);
	}

	/**
	 * Return property template associated to the current property definition
	 * @return
	 */
	public PropertyTemplate getPropertyTemplate();
	
}
