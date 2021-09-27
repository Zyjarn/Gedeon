package com.vten.gedeon.apiimpl.property;

import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.property.PropertyDefinition;
import com.vten.gedeon.api.property.PropertyTemplate;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;

import lombok.Getter;
import lombok.Setter;

public class PropertyDefinitionImpl extends PersistableObjectImpl implements PropertyDefinition{
	
	@Setter @Getter
	private Properties properties = new PropertiesImpl() ;
	
	private PropertyTemplate propTemplate;
	
	public PropertyDefinitionImpl(PropertyTemplate template) {
		propTemplate = template;
	}	

	@Override
	public PropertyTemplate getPropertyTemplate() {
		return propTemplate;
	}

}
