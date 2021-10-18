package com.vten.gedeon.connector.elastic.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.vten.gedeon.dao.data.GedeonDBObject;

import lombok.Getter;

public class SearchResponse extends ElasticResponse{
	
	@Getter
	List<GedeonDBObject> hits;

	public SearchResponse(String strJson) {
		super(strJson);
		JSONObject obj = new JSONObject(strJson);
		hits = new ArrayList<>();
		obj.getJSONObject(JS_HITS).getJSONArray(JS_HITS).forEach(hitElt -> 
			hits.add(jsonObjectToDbObject((JSONObject) hitElt)));
	}
	
}
