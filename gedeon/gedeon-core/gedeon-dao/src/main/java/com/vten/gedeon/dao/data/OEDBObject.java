package com.vten.gedeon.dao.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vten.gedeon.api.OEObject;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.OEConstants;

import lombok.Data;

@Data
public class OEDBObject {
	
	private String id;
	
	public OEDBObject() {
		//Default; nothing to do
	}
	
	public OEDBObject(OEObject source) {
		if(source.getProperties().containsProperty(OEConstants.PROP_ID)) {
			id = source.getProperties().get(OEConstants.PROP_ID).getObjectValue().toString();
		}
		setMapData(source.getProperties().stream()
				.collect(Collectors.toMap(Property::getSymbolicName,p -> Arrays.asList(p.getObjectValue()))));
	}
	
	private Map<String,List<Object>> mapData = new HashMap<>();
}
