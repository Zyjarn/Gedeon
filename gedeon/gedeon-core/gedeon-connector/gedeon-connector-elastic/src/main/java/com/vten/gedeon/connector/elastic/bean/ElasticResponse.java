package com.vten.gedeon.connector.elastic.bean;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vten.gedeon.dao.data.GedeonDBObject;

public abstract class ElasticResponse {
	
	protected static final String JS_SEQ_NO = "_seq_no";
	protected static final String JS_ID = "_id";
	protected static final String JS_SOURCE = "_source";
	protected static final String JS_HITS = "hits";
	private static final String JS_DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d*)";
	
	private String src;
	
	protected JSONObject jsonObj;

	ElasticResponse(String strJson) {
		src = strJson;
		jsonObj = new JSONObject(strJson);
	}
	
	/**
	 * Display raw data
	 */
	public String toString() {
		return src;
	}
	
	protected GedeonDBObject jsonObjectToDbObject(JSONObject obj) {
		GedeonDBObject dbObj = new GedeonDBObject();
		dbObj.setId(obj.getString(JS_ID));
		dbObj.setSeqNo(obj.getInt(JS_SEQ_NO));
		JSONObject sourceElt = obj.getJSONObject(JS_SOURCE);
		dbObj.setMapData(sourceElt.toMap().entrySet().stream().collect(
				Collectors.toMap(Map.Entry::getKey, e ->  jsonObjectValueToObject(e.getValue()))));
		return dbObj;
	}
	
	protected Object jsonObjectValueToObject(Object obj) {
		if(obj instanceof String) {
			if(((String) obj).matches(JS_DATE_PATTERN)) {
				return LocalDateTime.parse((String)obj);
			}
		} else if(obj instanceof JSONArray) {
			return ((JSONArray)obj).toList().stream()
				.map(o -> jsonObjectValueToObject(obj))
				.collect(Collectors.toList());
		}
		return obj;
	}
	
}
