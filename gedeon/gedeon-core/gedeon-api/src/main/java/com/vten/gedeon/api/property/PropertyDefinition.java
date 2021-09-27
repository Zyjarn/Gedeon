package com.vten.gedeon.api.property;

import com.vten.gedeon.api.OEObject;
import com.vten.gedeon.api.utils.OEConstants;
import com.vten.gedeon.model.property.PropertyType;

public interface PropertyDefinition extends OEObject{
	
	public default String getPropertyName() {
		return getPropertyTemplate().getProperties().get(OEConstants.PROP_PROPERTY_NAME).getStringValue();
	}
	
	public default PropertyType getType() {
		return PropertyType.valueOf(getPropertyTemplate().getProperties().get(OEConstants.PROP_PROPERTY_TYPE).getStringValue());
	}
	
	public default boolean isList() {
		return getPropertyTemplate().getProperties().get(OEConstants.PROP_PROPERTY_IS_LIST).getBooleanValue();
	}
	
	public default boolean isRequired() {
		return getProperties().get(OEConstants.PROP_PROPERTY_IS_REQUIRED).getBooleanValue();
	}
	
	public default void isRequired(boolean val) {
		getProperties().get(OEConstants.PROP_PROPERTY_IS_REQUIRED).setObjectValue(val);
	}

	/**
	 * Return property template associated to the current property definition
	 * @return
	 */
	public PropertyTemplate getPropertyTemplate();
	
}
