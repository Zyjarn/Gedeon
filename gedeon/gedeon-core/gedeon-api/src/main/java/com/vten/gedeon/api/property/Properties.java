package com.vten.gedeon.api.property;

import java.util.stream.Stream;

public interface Properties  extends Iterable<Property>{
	
	public void put(String propertyName, Property obj);
	
	public void put(Property obj);
	
	public void add(Property property);
	
	public Property get(String propertyName);
	
	public boolean containsProperty(String propertyName);
	
	public Stream<Property> stream();

}