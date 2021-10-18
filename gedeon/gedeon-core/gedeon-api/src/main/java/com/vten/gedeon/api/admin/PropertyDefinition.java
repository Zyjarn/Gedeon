package com.vten.gedeon.api.admin;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.api.utils.Setability;
import com.vten.gedeon.model.property.PropertyType;

public interface PropertyDefinition extends PersistableObject{
	
	public default String getDisplayName() {
		return getProperties().get(GedeonProperties.PROP_DISPLAY_NAME).getStringValue();
	}
	
	public default void setDisplayName(String displayName) {
		setPropertyValue(GedeonProperties.PROP_DISPLAY_NAME, displayName);
	}
	
	public default boolean isRequired() {
		return getProperties().get(GedeonProperties.PROP_DISPLAY_NAME).getBooleanValue();
	}
	
	public default void isRequired(boolean val) {
		setPropertyValue(GedeonProperties.PROP_IS_REQUIRED, val);
	}
	
	public default Setability getSetability() {
		return Setability.fromInt(getProperties().get(GedeonProperties.PROP_SETABILITY).getIntegerValue());
	}
	
	public default void setSetability(Setability displayName) {
		setPropertyValue(GedeonProperties.PROP_SETABILITY, displayName.getValue());
	}
	
	public default Object getDefaultValue() {
		return getProperties().get(GedeonProperties.PROP_DEFAULT_VALUE).getObjectValue();
	}
	
	public default void setDefaultValue(Object defaultValue) {
		setPropertyValue(GedeonProperties.PROP_DEFAULT_VALUE, defaultValue);
	}
	
	
	/**
	 * Return property template associated to the current property definition
	 * @return
	 */
	public default GedId getPropertyTemplateId(){
		return getProperties().get(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID).getIdValue();
	}
	
	/**
	 * Set the id of the associated property template
	 * - must be a valid entry template id for save
	 * @param propTpltId
	 */
	public default void setPropertyTemplateId(GedId propTpltId){
		setPropertyValue(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID, propTpltId);
	}

	/**
	 * Return property template associated to the current property definition
	 * @return
	 */
	public PropertyTemplate getPropertyTemplate();
	
	/**
	 * Set the associated property template of the current instance
	 * @param propertyTemplate
	 */
	public void setPropertyTemplate(PropertyTemplate propertyTemplate);
	
	/**
	 * Get the property's name corresponding to the property template name
	 * @return string representation of property name
	 */
	public default String getPropertyName() {
		return getPropertyTemplate().getProperties().get(GedeonProperties.PROP_NAME).getStringValue();
	}
	
	/**
	 * Get the accepted type for the property defined in property template
	 * @return PropertyType enum element
	 */
	public default PropertyType getType() {
		return PropertyType.valueOf(getPropertyTemplate().getProperties().get(GedeonProperties.PROP_PROPERTY_TYPE).getStringValue());
	}
	
	public default GedId getAssociatedClassId() {
		return getProperties().get(GedeonProperties.PROP_PARENT_CLASS_ID).getIdValue();
	}
	
	public default void setAssociatedClassId(GedId defaultValue) {
		setPropertyValue(GedeonProperties.PROP_PARENT_CLASS_ID, defaultValue); 
	}
	
	/**
	 * Get the class definition for which the current instance 
	 * of property definition belong
	 * @return parent class definition
	 */
	public ClassDefinition getAssociatedClass();
	
	public boolean isInherited();
}
