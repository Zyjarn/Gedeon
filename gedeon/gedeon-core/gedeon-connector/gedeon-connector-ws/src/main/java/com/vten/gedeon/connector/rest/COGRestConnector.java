package com.vten.gedeon.connector.rest;

import org.springframework.context.annotation.Configuration;

import com.vten.gedeon.dao.connector.GedeonDBConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;

@Configuration
public class COGRestConnector implements GedeonDBConnector{

	@Override
	public int initDB() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GedeonDBObject getObject(String className, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GedeonDBObject saveObject(String className, GedeonDBObject obj) {
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