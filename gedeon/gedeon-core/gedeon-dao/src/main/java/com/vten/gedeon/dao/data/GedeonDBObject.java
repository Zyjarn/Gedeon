package com.vten.gedeon.dao.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vten.gedeon.api.GedObject;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedeonProperties;

import lombok.Data;

@Data
public class GedeonDBObject {
	
	private String id;
	
	public GedeonDBObject() {
		//Default; nothing to do
	}
	
	public GedeonDBObject(GedObject source) {
		if(source.getProperties().containsProperty(GedeonProperties.PROP_ID)) {
			id = source.getProperties().get(GedeonProperties.PROP_ID).getObjectValue().toString();
		}
		setMapData(source.getProperties().stream()
				.collect(Collectors.toMap(Property::getSymbolicName,p -> Arrays.asList(p.getObjectValue()))));
	}
	
	private Map<String,List<Object>> mapData = new HashMap<>();
}
