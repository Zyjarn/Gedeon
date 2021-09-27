package com.vten.gedeon.dao.data;

import java.util.HashMap;
import java.util.Map;

import com.vten.gedeon.api.PersistableObject;

import lombok.Getter;

public class GedeonDBObject {
	
	@Getter
	private String id;
	@Getter
	private Map<String,Object> mapData = new HashMap<>();

	public GedeonDBObject(PersistableObject obj) {
		id = obj.getId().toString();
		obj.getProperties().forEach(p -> mapData.put(p.getSymbolicName(), p.getObjectValue()));
	}

	public GedeonDBObject() {
		// Nothing to do
	}

}
