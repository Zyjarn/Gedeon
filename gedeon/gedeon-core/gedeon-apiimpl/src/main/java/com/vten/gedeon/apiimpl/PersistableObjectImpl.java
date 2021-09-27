package com.vten.gedeon.apiimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.apiimpl.property.PropertyImpl;
import com.vten.gedeon.dao.PersistableObjectDAO;
import com.vten.gedeon.exception.OEErrorCode;
import com.vten.gedeon.exception.OERuntimeException;
import com.vten.gedeon.utils.SaveMode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class PersistableObjectImpl implements PersistableObject {
	
	@Autowired
	protected PersistableObjectDAO dao;
	
	@Setter
	@Getter
	private Properties properties = new PropertiesImpl();
	
	@Setter(AccessLevel.PROTECTED)
	@Getter
	private ClassDefinition classDefinition;
	
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private int seqNo = 0;
	
	private List<GedEvents> pendingEvents;
	
	@Override
	public void setPropertyValue(String propertyName, Object value) {
		Property property = getProperties().get(propertyName);
		if(property == null && seqNo == 0)
			getProperties().add(new PropertyImpl(propertyName,value));
		else if (property != null) {
			getProperties().get(propertyName).setObjectValue(value);
		} else 
			throw new OERuntimeException(OEErrorCode.OE1002, null);
	}
	

	@Override
	public String getClassName() {
		return classDefinition.getName();
	}

	@Override
	public void save(SaveMode mode) {
		dao.saveObject(this,mode);
	}

	@Override
	public void refresh() {
		PersistableObjectImpl refreshObject = (PersistableObjectImpl) dao.getObject(getClassName(), getId().toString());
		this.setProperties(refreshObject.getProperties());
		this.seqNo = refreshObject.getSeqNo();
	}
	
	@Override
	public List<GedEvents> getPendingEvents(){
		if(pendingEvents == null)
			pendingEvents = new ArrayList<>();
		return pendingEvents;
	}
}
