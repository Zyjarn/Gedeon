package com.vten.gedeon.daoimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.apiimpl.property.PropertyImpl;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.utils.SaveMode;

public abstract class GedeonDAO {
	
	protected GedFactory factory;
	protected GedeonDBConnector connector;

	@Autowired
	protected GedeonDAO(GedeonDBConnector dbConnect, GedFactory factory) {
		this.connector = dbConnect;
		this.factory = factory;
	}

	protected PersistableObject fillPersistableObjectInstance(PersistableObject instance, GedeonDBObject persistObject) {
		Properties props = new PropertiesImpl();
		//Add Id
		props.add(new PropertyImpl(GedeonProperties.PROP_ID, new GedId(persistObject.getId())));
		persistObject.getMapData().entrySet().stream()
				.forEach(e -> props.add(new PropertyImpl(e.getKey(), e.getValue())));
		//TODO add urn		
		instance.setProperties(props);
		return instance;
	}

	protected PersistableObject getObjectById(PersistableObject instance, String className, String id) {
		GedeonDBObject persistObject = this.connector.getObject(className, id);
		if (StringUtils.isBlank(persistObject.getId())) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1003,className, id);
		}
		return fillPersistableObjectInstance(instance, persistObject);
	}

	protected PersistableObject getObjectByName(String className, String name) {
		GedSearch searchByName = new GedSearch.SearchBuilder().selectAll().from(className)
				.where().equals(GedeonProperties.PROP_NAME, name).build(factory);
		List<PersistableObject> results = searchByName.search();
		if (results.isEmpty()) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1003,className, name);
		}
		return results.get(0);
	}

	public void saveObject(PersistableObject obj, SaveMode mode) {
		if (obj.getSeqNo() == 0) {
			obj.setAddedOn(LocalDateTime.now());
			obj.setAddedBy("System");
		}
		obj.setDateSaved(LocalDateTime.now());
		obj.setLastModifier("System");
		GedeonDBObject objToSave = new GedeonDBObject(obj);
		//TODO get table name ? 
		if(obj.getPendingEvents().contains(GedEvents.CREATE))
			objToSave = this.connector.createObject(obj.getClassName(), objToSave);
		else
			objToSave = this.connector.saveObject(obj.getClassName(), objToSave);
		obj.setId(new GedId(objToSave.getId()));
		((PersistableObjectImpl)obj).setSeqNo(objToSave.getSeqNo());
	}
	
	protected PersistableObject getInstanceByClassName(String className) {
		switch (className) {
		case GedeonProperties.CLASS_GEDCOLLECTION: {
			return this.factory.createGedCollection();
		}
		case GedeonProperties.CLASS_GEDDOCUMENT: {
			return this.factory.createGedDocument();
		}
		case GedeonProperties.CLASS_PROPERTYTEMPLATE: {
			return this.factory.createPropertyTemplate();
		}
		case GedeonProperties.CLASS_CLASSDEFINITION: {
			return this.factory.createClassDefinition();
		}
		case GedeonProperties.CLASS_PROPERTYDEFINITION: {
			return this.factory.createPropertyDefinition();
		}
		case GedeonProperties.CLASS_GEDFOLDER: {
			return this.factory.createGedFolder();
		}
		case GedeonProperties.CLASS_CONTAINMENTRELATIONSHIP: {
			return this.factory.createContainmentRelationship();
		}
		default:
			break;
		}
		throw new GedeonRuntimeException(
				String.format("Unable to instantiate object with className : '%s' ", className));
	}

}