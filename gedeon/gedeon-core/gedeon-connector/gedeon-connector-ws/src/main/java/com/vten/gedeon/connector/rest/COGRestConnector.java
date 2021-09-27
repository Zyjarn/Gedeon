package com.vten.gedeon.connector.rest;

import org.springframework.context.annotation.Configuration;

import com.vten.gedeon.dao.connector.OEDBConnector;
import com.vten.gedeon.dao.data.OEDBObject;

@Configuration
public class COGRestConnector implements OEDBConnector{

	@Override
	public int initDB() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OEDBObject getObject(String className, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OEDBObject saveObject(String className, OEDBObject obj) {
		// TODO Auto-generated method stub
		return obj;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObject(String className, String id) {
		// TODO Auto-generated method stub
		
	}

}
