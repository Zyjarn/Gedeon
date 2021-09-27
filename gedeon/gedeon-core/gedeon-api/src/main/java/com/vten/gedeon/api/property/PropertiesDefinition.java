package com.vten.gedeon.api.property;

import java.util.stream.Stream;

public interface PropertiesDefinition  extends Iterable<PropertyDefinition>{
	
	public void put(String propertyName, PropertyDefinition obj);
	
	public Stream<PropertyDefinition> stream();
	
	public void add(PropertyDefinition propertyDefinition);
	
	public PropertyDefinition get(String propertyName);
	
	public boolean containsProperty(String propertyName);

}