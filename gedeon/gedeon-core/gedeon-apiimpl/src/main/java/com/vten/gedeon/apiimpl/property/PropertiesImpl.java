package com.vten.gedeon.apiimpl.property;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.property.Property;

public class PropertiesImpl implements Properties{
	
	Map<String,Property> propertiesMap = new HashMap<>();

	@Override
	public Iterator<Property> iterator() {
		return propertiesMap.values().iterator();
	}
	
	@Override
	public Stream<Property> stream() {
		return propertiesMap.values().stream();
	}

	@Override
	public void put(String propertyName, Property obj) {
		propertiesMap.put(propertyName, obj);
	}
	
	@Override
	public void put(Property obj) {
		propertiesMap.put(obj.getSymbolicName(), obj);
	}

	@Override
	public void add(Property property) {
		propertiesMap.put(property.getSymbolicName(), property);
	}

	@Override
	public Property get(String propertyName) {
		return propertiesMap.get(propertyName);
	}

	@Override
	public boolean containsProperty(String propertyName) {
		return propertiesMap.containsKey(propertyName);
	}

	

}
