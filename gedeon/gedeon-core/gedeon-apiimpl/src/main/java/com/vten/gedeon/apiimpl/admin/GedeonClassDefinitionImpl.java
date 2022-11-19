package com.vten.gedeon.apiimpl.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.GedeonClassDefinition;
import com.vten.gedeon.api.admin.PropertiesDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.utils.SaveMode;

import lombok.Getter;

@Component
public class GedeonClassDefinitionImpl extends PersistableObjectImpl implements GedeonClassDefinition {

	private GedeonClassDefinition parentClassDefinition;

	@Getter
	private PropertiesDefinition propertiesDefinitions = new PropertiesDefinitionImpl();

	@Override
	public void addPropertyDefinition(PropertyDefinition propDef) {
		propertiesDefinitions.add(propDef);
		((PropertyDefinitionImpl) propDef).setAssociatedClass(this);
	}

	@Override
	public void refresh() {

		PersistableObject refreshObject = getGedeonCollection().getObject(getName(), getTableName());
		setProperties(refreshObject.getProperties());
		setSeqNo(refreshObject.getSeqNo());
		// Retrieve object class definition
		if (StringUtils.equals(getName(), GedeonProperties.CLASS_CLASSDEFINITION)) {
			setClassDefinition(this);
		} else {
			setClassDefinition((GedeonClassDefinition) getGedeonCollection().getObject(GedeonProperties.CLASS_CLASSDEFINITION,
					GedeonProperties.CLASS_CLASSDEFINITION));
		}
		// Retrieve parent class definition
		if (StringUtils.isNotBlank(getParentClassDefinitionId().getValue())) {
			parentClassDefinition = (GedeonClassDefinition) getGedeonCollection().getObject(getParentClassDefinitionId(),
					GedeonProperties.CLASS_CLASSDEFINITION);
		}
		// Retrieve properties definitions
		propertiesDefinitions.clear();
		getGedeonCollection().getPropertiesDefinitionForClass(this).forEach(this::addPropertyDefinition);
	}

	@Override
	public GedeonClassDefinition getParentClassDefinition() {
		if (parentClassDefinition == null) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1002, GedeonProperties.PROP_PARENT_CLASS_ID);
		}
		return parentClassDefinition;
	}

	@Override
	public void setParentClassDefinition(GedeonClassDefinition classDefinition) {
		parentClassDefinition = classDefinition;
		classDefinition.getPropertiesDefinitions().stream().forEach(propDef -> {
			addPropertyDefinition(getGedeonCollection().newCopyInstance(propDef));
		});
		setPropertyValue(GedeonProperties.PROP_PARENT_CLASS_ID, classDefinition.getId());
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
			propDef.save(mode);
		});
	}

}
