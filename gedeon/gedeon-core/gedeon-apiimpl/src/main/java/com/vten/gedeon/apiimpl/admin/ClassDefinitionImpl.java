package com.vten.gedeon.apiimpl.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertiesDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;
import com.vten.gedeon.dao.ClassDefinitionDAO;
import com.vten.gedeon.dao.PropertyDefinitionDAO;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.utils.SaveMode;

import lombok.Getter;

@Component
public class ClassDefinitionImpl extends PersistableObjectImpl implements ClassDefinition{
	
	@Autowired
	private ClassDefinitionDAO classDefDAO;
	
	@Autowired
	private PropertyDefinitionDAO propDefDAO;
	
	private ClassDefinition parentClassDefinition;
	
	@Getter
	private PropertiesDefinition propertiesDefinitions = new PropertiesDefinitionImpl();
	
	@Override
	public void addPropertyDefinition(PropertyDefinition propDef) {
		propertiesDefinitions.add(propDef);
		((PropertyDefinitionImpl)propDef).setAssociatedClass(this);
	}

	@Override
	public void refresh() {
		PersistableObjectImpl refreshObject = (PersistableObjectImpl) dao.getObject(getGedeonCollection(),getTableName(), getName(),false);
		setProperties(refreshObject.getProperties());
		setSeqNo(refreshObject.getSeqNo());
		//Retrieve object class definition
		if(StringUtils.equals(getName(),GedeonProperties.CLASS_CLASSDEFINITION)) 
			setClassDefinition(this);
		else
			setClassDefinition(classDefDAO.getObject(getGedeonCollection(),GedeonProperties.CLASS_CLASSDEFINITION));
		//Retrieve parent class definition
		if(StringUtils.isNotBlank(getParentClassDefinitionId().getValue()))
			parentClassDefinition = classDefDAO.getObject(getGedeonCollection(),getParentClassDefinitionId());
		//Retrieve properties definitions
		propertiesDefinitions.clear();
		propDefDAO.getPropertiesDefinitionForClass(getGedeonCollection(),this).forEach(this::addPropertyDefinition);
	}
	
	@Override
	public ClassDefinition getParentClassDefinition() {
		if(parentClassDefinition == null) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1002,GedeonProperties.PROP_PARENT_CLASS_ID);
		}
		return parentClassDefinition;
	}

	@Override
	public void setParentClassDefinition(ClassDefinition classDefinition) {
		parentClassDefinition = classDefinition;
		classDefinition.getPropertiesDefinitions().stream().forEach(propDef -> {
			addPropertyDefinition(propDefDAO.newCopyInstance(propDef));
		});
		setPropertyValue(GedeonProperties.PROP_PARENT_CLASS_ID,classDefinition.getId());
	}
	
	@Override
	public String getClassName() {
		return GedeonProperties.CLASS_CLASSDEFINITION;
	}

	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_CLASSDEFINITION;
	}

	@Override
	public void save(SaveMode mode) {
		super.save(mode);
		propertiesDefinitions.forEach(propDef -> {
			propDef.setAssociatedClassId(getId());
			propDef.save(mode);}
		);
	}

	
}
