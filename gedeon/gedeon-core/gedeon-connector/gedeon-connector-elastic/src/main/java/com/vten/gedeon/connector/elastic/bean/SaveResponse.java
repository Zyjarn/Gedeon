package com.vten.gedeon.connector.elastic.bean;

import lombok.Getter;

public class SaveResponse extends ElasticResponse{
	
	@Getter
	private String id;
	
	@Getter
	private int seqNo;

	public SaveResponse(String strJson) {
		super(strJson);
		id = jsonObj.getString(JS_ID);
		seqNo = jsonObj.getInt(JS_SEQ_NO);
	}
	
	
}
