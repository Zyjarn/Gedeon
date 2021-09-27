package com.vten.gedeon.dao;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.apiimpl.OEFactoryImpl;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.apiimpl.property.PropertyImpl;
import com.vten.gedeon.dao.connector.OEDBConnector;
import com.vten.gedeon.dao.data.OEDBObject;
import com.vten.gedeon.utils.SaveMode;

public abstract class OpenECMDAO {
	protected OEFactoryImpl factory;
	protected OEDBConnector connector;

	@Autowired
	public OpenECMDAO(final OEDBConnector dbConnect) {
		this.connector = dbConnect;
	}

	private void fillPersistableObjectInstance(PersistableObject instance, OEDBObject persistObject) {
		Properties props = (Properties) new PropertiesImpl();
		persistObject.getMapData().entrySet().stream()
				.forEach(e -> props.add(new PropertyImpl((String) e.getKey(), e.getValue())));
		instance.setProperties(props);
	}

	protected PersistableObject getObjectById(PersistableObject instance, String className, String id) {
		OEDBObject persistObject = this.connector.getObject(className, id);
		if (StringUtils.isBlank(persistObject.getId())) {
			return null;
		}
		fillPersistableObjectInstance(instance, persistObject);
		return instance;
	}

	protected PersistableObject getObjectByName(PersistableObject instance, String className, String name) {
		OEDBObject persistObject = this.connector.getObject(className, name);
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
		OEDBObject objToSave = new OEDBObject(obj);
		this.connector.saveObject(obj.getClassName(), objToSave);
		SaveMode.REFRESH.equals((Object) mode);
	}

}