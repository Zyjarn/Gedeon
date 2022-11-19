package com.vten.gedeon.apiimpl;

import java.util.ArrayList;
import java.util.List;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.GedeonClassDefinition;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.utils.SaveMode;

import lombok.Getter;
import lombok.Setter;

public abstract class PersistableObjectImpl implements PersistableObject {

	@Setter
	@Getter
	private Properties properties = new PropertiesImpl();

	private GedeonClassDefinition classDefinition;

	@Getter
	@Setter
	private GedeonCollection gedeonCollection;

	@Getter
	@Setter
	private int seqNo = 0;

	private List<GedEvents> pendingEvents;

	@Override
	public void setPropertyValue(String propertyName, Object value) {
		Property property = getProperties().get(propertyName);
		if (property == null) {
			getProperties().add(new Property(propertyName, value));
		} else {
			property.setObjectValue(value);
		}
	}

	@Override
	public String getClassName() {
		return getClassDefinition().getName();
	}

	@Override
	public void save(SaveMode mode) {
		gedeonCollection.saveObject(this, mode);
	}

	@Override
	public void refresh() {
		PersistableObject refreshObject = gedeonCollection.refreshObject(this);
		setProperties(refreshObject.getProperties());
		setId(refreshObject.getId());
		seqNo = refreshObject.getSeqNo();
		// Why ?
		classDefinition = null;
	}

	@Override
	public void delete() {
		// Add delete to pending event
		getPendingEvents().add(GedEvents.DELETE);
	}

	@Override
	public void setClassDefinition(GedeonClassDefinition classDef) {
		if ((classDef == null) || (classDef.getId() == null)) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1004);
		}
		classDefinition = classDef;
		setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS, classDef.getId());
	}

	@Override
	public GedeonClassDefinition getClassDefinition() {
		if (!getProperties().containsProperty(GedeonProperties.PROP_OBJECT_CLASS)) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1002, GedeonProperties.PROP_OBJECT_CLASS);
		} else if (classDefinition == null) {
			classDefinition = (GedeonClassDefinition) gedeonCollection.getObject(
					getProperties().get(GedeonProperties.PROP_OBJECT_CLASS).getIdValue(),
					GedeonProperties.CLASS_CLASSDEFINITION);
		}
		return classDefinition;
	}

	@Override
	public List<GedEvents> getPendingEvents() {
		if (pendingEvents == null) {
			pendingEvents = new ArrayList<>();
		}
		return pendingEvents;
	}

}
