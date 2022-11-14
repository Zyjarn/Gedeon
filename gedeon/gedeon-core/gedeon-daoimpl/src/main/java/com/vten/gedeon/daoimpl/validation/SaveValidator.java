package com.vten.gedeon.daoimpl.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.Setability;
import com.vten.gedeon.model.property.PropertyType;

@Service
public class SaveValidator {

	public boolean classDefinitionValidationOnSave(PersistableObject obj) {
		ClassDefinition classDef = obj.getClassDefinition();

		boolean onCreate = obj.getPendingEvents().contains(GedEvents.CREATE);
		boolean onUpdate = obj.getPendingEvents().contains(GedEvents.UPDATE);

		/*
		 * Validate properties
		 */
		for (PropertyDefinition propDef : classDef.getPropertiesDefinitions()) {

			Property prop = obj.getProperties().get(propDef.getName());

			// Check if required properties are filled
			if (propDef.isRequired() && isEmptyProperty(propDef, prop)) {
				if (onCreate && (propDef.getDefaultValue() != null)) {
					// On create set default value on properties and don't block save
					obj.getProperties().put(new Property("", propDef.getDefaultValue()));
				} else {
					if (!onCreate && Setability.ONCREATE.equals(propDef.getSetability())) {
					}
					return false;
				}

			}
			// Check if property is of correct type
			if (!matchType(propDef, prop)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the given property is null or is value is null
	 * 
	 * @param propDef
	 * @param prop
	 * @return
	 */
	protected boolean isEmptyProperty(PropertyDefinition propDef, Property prop) {
		if ((prop == null) || (prop.getObjectValue() == null)
				|| (PropertyType.STRING.equals(propDef.getType()) && StringUtils.isBlank(prop.getStringValue()))) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the given property object value match with the given property
	 * definition
	 * 
	 * @param propDef model propertyDefinition
	 * @param prop    Property ro check
	 * @return true if property match propertyDefinition
	 */
	protected boolean matchType(PropertyDefinition propDef, Property prop) {
		// All null values are validated
		if (prop.getObjectValue() == null) {
			return true;
		}
		try {
			switch (propDef.getType()) {
			case BOOLEAN: {
				if (propDef.isList()) {
					prop.getListBooleanValue();
				} else {
					prop.getBooleanValue();
				}
			}
				break;
			case DATE: {
				if (propDef.isList()) {
					prop.getListBooleanValue();
				} else {
					prop.getBooleanValue();
				}
			}
				break;
			case DOUBLE: {
				if (propDef.isList()) {
					prop.getListDoubleValue();
				} else {
					prop.getDoubleValue();
				}
			}
				break;
			case ID: {
				if (propDef.isList()) {
					prop.getListIdValue();
				} else {
					prop.getIdValue();
				}
			}
				break;
			case INTEGER: {
				if (propDef.isList()) {
					prop.getListIntegerValue();
				} else {
					prop.getIntegerValue();
				}
			}
				break;
			case LONG: {
				if (propDef.isList()) {
					prop.getListLongValue();
				} else {
					prop.getLongValue();
				}
			}
				break;
			case OBJECTID: {
				if (propDef.isList()) {
					prop.getListBooleanValue();
				} else {
					prop.getBooleanValue();
				}
			}
				break;
			case STRING: {
				if (propDef.isList()) {
					prop.getListStringValue();
				} else {
					prop.getStringValue();
				}
			}
				break;
			default:
				// Unknow type
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
