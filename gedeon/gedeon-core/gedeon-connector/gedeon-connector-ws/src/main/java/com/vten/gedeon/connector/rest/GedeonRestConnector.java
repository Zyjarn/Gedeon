package com.vten.gedeon.connector.rest;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.connector.rest.config.GedeonRestConnectorProperties;
import com.vten.gedeon.dao.data.GedeonDBObject;

@Service
@EnableConfigurationProperties(value = GedeonRestConnectorProperties.class)
public class GedeonRestConnector implements GedeonDBConnector {

	@Override
	public int initDB() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GedeonDBObject getObject(String collectionName, String className, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GedeonDBObject saveObject(String collectionName, String className, GedeonDBObject obj) {
		// TODO Auto-generated method stub
		return obj;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObject(String collectionName, String className, String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCollection(String collectionName) {

	}

	@Override
	public List<GedeonDBObject> search(String collectionName, String className, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GedeonDBObject createObject(String collectionName, String className, GedeonDBObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
