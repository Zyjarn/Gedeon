package com.vten.gedeon.daoimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.property.Property;
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
		instance.setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS, 
				new GedId((String) persistObject.getMapData().get(GedeonProperties.PROP_OBJECT_CLASS)));
//		ClassDefinition classDef = instance.getClassDefinition();
		persistObject.getMapData().entrySet().stream()
				.forEach(e -> props.add(getProperty(/*classDef,*/e.getKey(), e.getValue())));
		//Set properties on instance
		instance.setProperties(props);
		//Set seqNo
		((PersistableObjectImpl)instance).setSeqNo(persistObject.getSeqNo());
		return instance;
	}
	
	protected Property getProperty(/*ClassDefinition classDef,*/ String key, Object value) {
		Property prop = new PropertyImpl();
		prop.setSymbolicName(key);
		prop.setObjectValue(value);
		/*PropertyDefinition propDef = classDef.getPropertiesDefinitions().get(key);
		if(propDef != null && PropertyType.ID.equals(propDef.getPropertyTemplate().getType()))
			prop.setObjectValue(new GedId((String)value));		*/
		return prop;
	}

	protected PersistableObject getObjectById(PersistableObject instance, String className, String id) {
		GedeonDBObject persistObject = this.connector.getObject(instance.getGedeonCollection().getName(), 
				className, id);
		if (StringUtils.isBlank(persistObject.getId())) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1003,className, id);
		}
		return fillPersistableObjectInstance(instance, persistObject);
	}

	protected PersistableObject getObjectByName(GedeonCollection collection,String className, String name) {
		GedSearch searchByName = new GedSearch.SearchBuilder().selectAll().from(className)
				.where().equals(GedeonProperties.PROP_NAME, name).build(collection);
		List<PersistableObject> results = searchByName.search();
		if (results.isEmpty()) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1003,className, name);
		}
		return results.get(0);
	}
	
	protected void setPropertiesOnSave(PersistableObject obj) {
		if (obj.getSeqNo() == 0) {
			obj.setAddedOn(LocalDateTime.now());
			obj.setAddedBy("System");
		}
		obj.setDateSaved(LocalDateTime.now());
		obj.setLastModifier("System");
	}

	public void saveObject(PersistableObject obj, SaveMode mode) {
		setPropertiesOnSave(obj);
		//Collection name, empty for gedeonCollection
		String colName = obj instanceof GedeonCollection ? StringUtils.EMPTY : 
			obj.getGedeonCollection().getName();
		
		GedeonDBObject objToSave = new GedeonDBObject(obj);
		//TODO get table name ? 
		if(obj.getPendingEvents().contains(GedEvents.CREATE))
			objToSave = this.connector.createObject(colName,obj.getClassName(), objToSave);
		else
			objToSave = this.connector.saveObject(colName,obj.getClassName(), objToSave);
		obj.setId(new GedId(objToSave.getId()));
		((PersistableObjectImpl)obj).setSeqNo(objToSave.getSeqNo());
	}
	
	protected PersistableObject getInstanceByClassName(GedeonCollection collection,String className) {
		PersistableObject obj;
		switch (className) {
		case GedeonProperties.CLASS_GEDCOLLECTION: {
			obj = this.factory.createGedCollection();
		}break;
		case GedeonProperties.CLASS_GEDDOCUMENT: {
			obj = this.factory.createGedDocument(collection);
		}break;
		case GedeonProperties.CLASS_PROPERTYTEMPLATE: {
			obj = this.factory.createPropertyTemplate(collection);
		}break;
		case GedeonProperties.CLASS_CLASSDEFINITION: {
			obj = this.factory.createClassDefinition(collection);
		}break;
		case GedeonProperties.CLASS_PROPERTYDEFINITION: {
			obj = this.factory.createPropertyDefinition(collection);
		}break;
		case GedeonProperties.CLASS_GEDFOLDER: {
			obj = this.factory.createGedFolder(collection);
		}break;
		case GedeonProperties.CLASS_CONTAINMENTRELATIONSHIP: {
			obj = this.factory.createContainmentRelationship(collection);
		}break;
		default:
			throw new GedeonRuntimeException(
					String.format("Unable to instantiate object with className : '%s' ", className));
		}
		return obj;
		
	}

}