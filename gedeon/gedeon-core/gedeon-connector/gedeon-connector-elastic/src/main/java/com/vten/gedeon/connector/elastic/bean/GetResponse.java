package com.vten.gedeon.connector.elastic.bean;

import com.vten.gedeon.dao.data.GedeonDBObject;

import lombok.Getter;

public class GetResponse extends ElasticResponse{
	
	@Getter
	private GedeonDBObject dbObject;

	public GetResponse(String strJson) {
		super(strJson);
		dbObject = jsonObjectToDbObject(jsonObj);
	}
	
}
