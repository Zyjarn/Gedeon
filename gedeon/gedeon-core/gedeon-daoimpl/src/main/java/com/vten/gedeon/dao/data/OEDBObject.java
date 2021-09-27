package com.vten.gedeon.dao.data;

import java.util.HashMap;
import java.util.Map;

import com.vten.gedeon.api.PersistableObject;

import lombok.Getter;

public class OEDBObject {
	
	@Getter
	private String id;
	@Getter
	private Map<String,Object> mapData = new HashMap<>();

	public OEDBObject(PersistableObject obj) {
		id = obj.getId().toString();
		obj.getProperties().forEach(p -> mapData.put(p.getSymbolicName(), p.getObjectValue()));
	}

	public OEDBObject() {
		// Nothing to do
	}

}
