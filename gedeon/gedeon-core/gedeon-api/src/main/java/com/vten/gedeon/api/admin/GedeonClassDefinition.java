package com.vten.gedeon.api.admin;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;

public interface GedeonClassDefinition extends PersistableObject{
	
	public default boolean isSystem() {
		return getProperties().get(GedeonProperties.PROP_IS_SYSTEM).getBooleanValue();
	}
	
	public default void isSystem(boolean isSystem) {
		setPropertyValue(GedeonProperties.PROP_IS_SYSTEM, isSystem);
	}
	
	public default boolean isAbstract() {
		return getProperties().get(GedeonProperties.PROP_IS_ABSTRACT).getBooleanValue();
	}
	
	public default void isAbstract(boolean isAbstract) {
		setPropertyValue(GedeonProperties.PROP_IS_ABSTRACT, isAbstract);
	}
	
	public default boolean isFinal() {
		return getProperties().get(GedeonProperties.PROP_IS_FINAL).getBooleanValue();
	}
	
	public default void isFinal(boolean isFinal) {
		setPropertyValue(GedeonProperties.PROP_IS_FINAL, isFinal);
	}
	
	public default GedId getParentClassDefinitionId() {
		Object o = getProperties().get(GedeonProperties.PROP_PARENT_CLASS_ID).getObjectValue();
		if(o instanceof String) {
			setPropertyValue(GedeonProperties.PROP_PARENT_CLASS_ID, new GedId((String) o));
		}
		return getProperties().get(GedeonProperties.PROP_PARENT_CLASS_ID).getIdValue();
	}
	
	public GedeonClassDefinition getParentClassDefinition();
	public void setParentClassDefinition(GedeonClassDefinition classDefinition);
	public PropertiesDefinition getPropertiesDefinitions();
	public void addPropertyDefinition(PropertyDefinition prop);
	
}
