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
	public OpenECMDAO(OEDBConnector dbConnect) {
		connector = dbConnect;
	}
	
	protected PersistableObject getObject(PersistableObject instance, String className, String id) {
		OEDBObject persistObject = connector.getObject(className, id);
		if(StringUtils.isBlank(persistObject.getId()))
			return null;
		Properties props = new PropertiesImpl();
		persistObject.getMapData().entrySet().stream()
			.forEach(e -> props.add(new PropertyImpl(e.getKey(),e.getValue())));
		instance.setProperties(props);
		return instance;
	}
	
	public void saveObject(PersistableObject obj, SaveMode mode) {
		//Check creation by sequence number
		if(obj.getSeqNo() == 0) {
			obj.setAddedOn(LocalDateTime.now());
			//TODO handle user
			obj.setAddedBy("System");
		}
		obj.setDateSaved(LocalDateTime.now());
		obj.setLastModifier("System");
		
		
		OEDBObject objToSave = new OEDBObject(obj);
		connector.saveObject(obj.getClassName(), objToSave);
		if(SaveMode.REFRESH.equals(mode)) {
			//TODO handle return
		}
	}

}
