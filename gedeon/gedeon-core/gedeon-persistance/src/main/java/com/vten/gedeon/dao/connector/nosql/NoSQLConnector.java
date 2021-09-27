package com.vten.gedeon.dao.connector.nosql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vten.gedeon.dao.connector.OEDBConnector;

public abstract class NoSQLConnector implements OEDBConnector{

	protected static final Logger LOG = LoggerFactory.getLogger(NoSQLConnector.class);
}
