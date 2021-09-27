package com.vten.gedeon.apiimpl;

import org.springframework.stereotype.Component;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.PropertiesDefinition;
import com.vten.gedeon.api.property.PropertyDefinition;
import com.vten.gedeon.apiimpl.property.PropertiesDefinitionImpl;

import lombok.Getter;

@Component
public class ClassDefinitionImpl extends PersistableObjectImpl implements ClassDefinition{
	
	@Getter
	PropertiesDefinition propertiesDefinitions = new PropertiesDefinitionImpl();

	@Override
	public void addPropertyDefinition(PropertyDefinition propDef) {
		propertiesDefinitions.add(propDef);
	}

	@Override
	public String getClassName() {
		return ClassDefinition.class.getSimpleName();
	}

}
