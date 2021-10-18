package com.vten.gedeon.api.admin;

import java.util.List;
import java.util.stream.Stream;

public interface PropertiesDefinition  extends Iterable<PropertyDefinition>{
	
	Stream<PropertyDefinition> stream();
	
	void add(PropertyDefinition propertyDefinition);
	
	void addAll(PropertiesDefinition propertiesDefinition);
	
	void addAll(List<PropertyDefinition> propertiesDefinition);
	
	void remove(PropertyDefinition propertyDefinition);
	
	void removeAll(List<PropertyDefinition> propertiesDefinition);
	
	PropertyDefinition get(String propertyName);
	
	boolean containsProperty(String propertyName);

	void clear();

	void save();

}