package com.vten.gedeon.apiimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.apiimpl.property.PropertyImpl;
import com.vten.gedeon.dao.PersistableObjectDAO;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.utils.SaveMode;

import lombok.Getter;
import lombok.Setter;

public abstract class PersistableObjectImpl implements PersistableObject {
	
	@Autowired
	protected PersistableObjectDAO dao;
	
	@Setter
	@Getter
	private Properties properties = new PropertiesImpl();
	
	@Setter
	private ClassDefinition classDefinition;
	
	@Getter
	@Setter
	private int seqNo = 0;
	
	private List<GedEvents> pendingEvents;
	
	/**
	 * Function use to retrieve the object by dao
	 * @return
	 */
	public abstract String getTableName();
	
	
	@Override
	public void setPropertyValue(String propertyName, Object value) {
		Property property = getProperties().get(propertyName);
		if(property == null)
			getProperties().add(new PropertyImpl(propertyName,value));
		else
			property.setObjectValue(value);
//		else if (property != null) {
//			getProperties().get(propertyName).setObjectValue(value);
//		} 
		//else 
		//throw new GedeonRuntimeException(GedeonErrorCode.OE1002,propertyName);
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
		PersistableObjectImpl refreshObject = (PersistableObjectImpl) dao.getObject(getTableName(), getId().toString());
		this.setProperties(refreshObject.getProperties());
		setId(refreshObject.getId());
		this.seqNo = refreshObject.getSeqNo();
		classDefinition = null;
	}
	
	@Override
	public ClassDefinition getClassDefinition() {
		if(!getProperties().containsProperty(GedeonProperties.PROP_OBJECT_CLASS)) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1002, GedeonProperties.PROP_OBJECT_CLASS);
		} else if(classDefinition == null) {
			classDefinition = (ClassDefinition) dao.getObject(GedeonProperties.CLASS_CLASSDEFINITION, 
					getProperties().get(GedeonProperties.PROP_OBJECT_CLASS).getIdValue().getValue());
		}
		return classDefinition;
	}
	
	@Override
	public List<GedEvents> getPendingEvents(){
		if(pendingEvents == null)
			pendingEvents = new ArrayList<>();
		return pendingEvents;
	}
	
	
}
