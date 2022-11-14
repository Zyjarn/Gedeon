package com.vten.gedeon.dao.data;

import java.util.HashMap;
import java.util.Map;

import com.vten.gedeon.api.GedObject;
import com.vten.gedeon.api.utils.GedeonProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GedeonDBObject {

	private String id;

	private String gedCollection;

	private int seqNo;

	private Map<String, Object> mapData = new HashMap<>();

	public GedeonDBObject(GedObject source) {
		if (source.getProperties().containsProperty(GedeonProperties.PROP_ID)) {
			id = source.getProperties().get(GedeonProperties.PROP_ID).getObjectValue().toString();
		}

		// TODO andle GedId/PropertyType here
		setMapData(source.getProperties().stream().filter(p -> !p.getSymbolicName().equals(GedeonProperties.PROP_ID))
				.collect(HashMap::new, (m, v) -> m.put(v.getSymbolicName(), v.getObjectValue()), HashMap::putAll));
	}

}
