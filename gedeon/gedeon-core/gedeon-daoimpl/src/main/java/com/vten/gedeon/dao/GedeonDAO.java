package com.vten.gedeon.dao;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.apiimpl.GedFactoryImpl;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.apiimpl.property.PropertyImpl;
import com.vten.gedeon.dao.connector.GedeonDBConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;
import com.vten.gedeon.utils.SaveMode;

public abstract class GedeonDAO {
	protected GedFactoryImpl factory;
	protected GedeonDBConnector connector;

	@Autowired
	public GedeonDAO(final GedeonDBConnector dbConnect) {
		this.connector = dbConnect;
	}

	private void fillPersistableObjectInstance(PersistableObject instance, GedeonDBObject persistObject) {
		Properties props = (Properties) new PropertiesImpl();
		persistObject.getMapData().entrySet().stream()
				.forEach(e -> props.add(new PropertyImpl((String) e.getKey(), e.getValue())));
		instance.setProperties(props);
	}

	protected PersistableObject getObjectById(PersistableObject instance, String className, String id) {
		GedeonDBObject persistObject = this.connector.getObject(className, id);
		if (StringUtils.isBlank(persistObject.getId())) {
			return null;
		}
		fillPersistableObjectInstance(instance, persistObject);
		return instance;
	}

	protected PersistableObject getObjectByName(PersistableObject instance, String className, String name) {
		GedeonDBObject persistObject = this.connector.getObject(className, name);
		if (StringUtils.isBlank(persistObject.getId())) {
			return null;
		}
		fillPersistableObjectInstance(instance, persistObject);
		return instance;
	}

	public void saveObject(PersistableObject obj, SaveMode mode) {
		if (obj.getSeqNo() == 0) {
			obj.setAddedOn(LocalDateTime.now());
			obj.setAddedBy("System");
		}
		obj.setDateSaved(LocalDateTime.now());
		obj.setLastModifier("System");
		GedeonDBObject objToSave = new GedeonDBObject(obj);
		this.connector.saveObject(obj.getClassName(), objToSave);
		SaveMode.REFRESH.equals((Object) mode);
	}

}