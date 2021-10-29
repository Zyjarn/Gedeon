package com.vten.gedeon.connector.rest;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;

@Configuration
public class COGRestConnector implements GedeonDBConnector{

	@Override
	public int initDB() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GedeonDBObject getObject(String collectionName,String className, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GedeonDBObject saveObject(String collectionName,String className, GedeonDBObject obj) {
		// TODO Auto-generated method stub
		return obj;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObject(String collectionName,String className, String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GedeonDBObject> search(String collectionName,String className, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GedeonDBObject createObject(String collectionName,String className, GedeonDBObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
