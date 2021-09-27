package com.vten.gedeon.apiimpl.property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.vten.gedeon.api.property.PropertiesDefinition;
import com.vten.gedeon.api.property.PropertyDefinition;
import com.vten.gedeon.exception.OERuntimeException;
import com.vten.gedeon.exception.OEErrorCode;

public class PropertiesDefinitionImpl implements PropertiesDefinition{
	
	private List<PropertyDefinition> dataHandler = new ArrayList<>();
	
	@Override
	public Iterator<PropertyDefinition> iterator() {
		return dataHandler.iterator();
	}

	@Override
	public void put(String propertyName, PropertyDefinition obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(PropertyDefinition propertyDefinition) {
		dataHandler.add(propertyDefinition);
	}

	@Override
	public PropertyDefinition get(String propertyName) {
		try {
			return dataHandler.stream()
					.filter(propDef -> StringUtils.equals(propertyName, propDef.getPropertyName()))
					.findFirst()
					.orElseThrow();
		} catch(NoSuchElementException e) {
			throw new OERuntimeException(OEErrorCode.OE1001, e);
		}
	}

	@Override
	public boolean containsProperty(String propertyName) {
		if(StringUtils.isBlank(propertyName))
			return false;
		return dataHandler.stream().anyMatch(propDef -> StringUtils.equals(propertyName, propDef.getPropertyName()));
	}

	@Override
	public Stream<PropertyDefinition> stream() {
		return dataHandler.stream();
	}

}
