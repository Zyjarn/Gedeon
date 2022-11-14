package com.vten.gedeon.connector;

import java.util.List;

import javax.annotation.PreDestroy;

import com.vten.gedeon.dao.data.GedeonDBObject;

public interface GedeonDBConnector
{
    int initDB();
    
    GedeonDBObject getObject(String collectionName,String className, String id);
    
    GedeonDBObject createObject(String collectionName,String objectName, GedeonDBObject obj);
    
    GedeonDBObject saveObject(String collectionName,String objectName, GedeonDBObject obj);
    
    void deleteObject(String collectionName, String objectName, String id);

    void deleteCollection(String collectionName);
    
    List<GedeonDBObject> search(String collectionName, String objectName,String query);
    
    @PreDestroy
    void cleanUp();
}