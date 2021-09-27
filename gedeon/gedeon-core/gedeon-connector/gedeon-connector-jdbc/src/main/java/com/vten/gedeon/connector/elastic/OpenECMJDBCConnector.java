package com.vten.gedeon.connector.elastic;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vten.gedeon.dao.connector.nosql.NoSQLConnector;
import com.vten.gedeon.dao.data.OEDBObject;

@Configuration
public class OpenECMJDBCConnector extends NoSQLConnector{
	
	private static final Logger LOG = LoggerFactory.getLogger(OpenECMJDBCConnector.class);

	@Value("${elasticsearch.host}")
    private String elasticsearchHost; 
	
	@Value("${elasticsearch.port}")
    private int elasticsearchPort; 
	
	@Value("${elasticsearch.protocol}")
    private String elasticsearchProtocol; 
	
	private Object client;
	
	@Bean(destroyMethod = "close")
	protected Object getClient() {
		//Security
		return null;
	
	}
	
	@PostConstruct	
    public void init() {
        client = getClient();
    }

	@Override
	public int initDB() {
		return 0;
	}

	@Override
	public OEDBObject getObject(String className, String id) {
		OEDBObject matchObject = new OEDBObject();
		
		return matchObject;
	}

	@Override
	public OEDBObject saveObject(String className, OEDBObject obj) {

		return null;
	}
	
	@Override
	public void deleteObject(String className, String id) {

	}
	
	public void search() {

	}

	@Override
	public void cleanUp() {
	}
	
}
