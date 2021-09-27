package com.vten.gedeon.dao.connector;

import javax.annotation.PreDestroy;

import com.vten.gedeon.dao.data.OEDBObject;

public interface OEDBConnector {
	
	/**
	 * Initialize database for application
	 * @return 0 for fail, 1 for ok, 2 for already exists
	 */
	public int initDB();

	/**
	 * Retrieve object in configured database
	 * @return
	 */
	public OEDBObject getObject(String className, String id);
	
	/**
	 * Save object to configured database
	 * @param className
	 * @param obj
	 */
	public OEDBObject saveObject(String className, OEDBObject obj);
	
	/**
	 * Save object to configured database
	 * @param className
	 * @param obj
	 */
	public void deleteObject(String className, String id);
	
	/**
	 * Clean objects like connection when connector is closed
	 */
	@PreDestroy
	public void cleanUp();
}
