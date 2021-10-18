package com.vten.gedeon.apiimpl.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.vten.gedeon.api.admin.PropertiesDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.utils.SaveMode;

public class PropertiesDefinitionImpl implements PropertiesDefinition{
	
	private List<PropertyDefinition> dataHandler = new ArrayList<>();
	private List<PropertyDefinition> deletedHandler = new ArrayList<>();
	
	@Override
	public Iterator<PropertyDefinition> iterator() {
		return dataHandler.iterator();
	}

	@Override
	public void add(PropertyDefinition propertyDefinition) {
		dataHandler.add(propertyDefinition);
	}
	
	@Override
	public void addAll(PropertiesDefinition propertiesDefinition) {
		dataHandler.addAll(propertiesDefinition.stream().collect(Collectors.toList()));
	}

	@Override
	public void addAll(List<PropertyDefinition> propertiesDefinition) {
		dataHandler.addAll(propertiesDefinition);
	}

	@Override
	public PropertyDefinition get(String propertyName) {
		try {
			return dataHandler.stream()
					.filter(propDef -> StringUtils.equals(propertyName, propDef.getPropertyName()))
					.findFirst()
					.orElseThrow();
		} catch(NoSuchElementException e) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE1001, e);
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
	
	@Override
	public void clear() {
		dataHandler.clear();
	}

	@Override
	public void remove(PropertyDefinition propertyDefinition) {
		//TODO 
		dataHandler.remove(propertyDefinition);
		if(propertyDefinition.getSeqNo() != 0 && propertyDefinition.isInherited())
			deletedHandler.add(propertyDefinition);
	}

	@Override
	public void removeAll(List<PropertyDefinition> propertiesDefinition) {
		//TODO
	}

	@Override
	public void save() {
		dataHandler.forEach(propDef -> propDef.save(SaveMode.REFRESH));
		//deletedHandler.forEach(propDef -> propDef.delete())
	}

}
