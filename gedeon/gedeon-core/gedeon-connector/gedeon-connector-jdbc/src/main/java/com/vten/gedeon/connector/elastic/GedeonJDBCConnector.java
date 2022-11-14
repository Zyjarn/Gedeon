package com.vten.gedeon.connector.elastic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vten.gedeon.dao.connector.nosql.DatabaseConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class GedeonJDBCConnector extends DatabaseConnector {

	@Value("${jdbc.driver}")
	private String jdbcDriver;

	@Value("${jdbc.url}")
	private String jdbcUrl;

	private Connection client;

	@Bean(destroyMethod = "close")
	protected Connection getClient() {
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(jdbcUrl, "toto", "passwd");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
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
	public GedeonDBObject getObject(String collectionName, String className, String id) {

		GedeonDBObject matchObject = new GedeonDBObject();

		return matchObject;
	}

	@Override
	public GedeonDBObject saveObject(String collectionName, String className, GedeonDBObject obj) {

		return null;
	}

	@Override
	public void deleteObject(String collectionName, String className, String id) {

	}

	@Override
	public void deleteCollection(String collectionName) {

	}

	public void search() {

	}

	@Override
	public void cleanUp() {
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
