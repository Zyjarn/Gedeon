package com.vten.gedeon.connector;

import java.util.List;

import javax.annotation.PreDestroy;

import com.vten.gedeon.dao.data.GedeonDBObject;

public interface GedeonDBConnector
{
    int initDB();
    
    GedeonDBObject getObject(String className, String id);
    
    GedeonDBObject createObject(String className, GedeonDBObject obj);
    
    GedeonDBObject saveObject(String className, GedeonDBObject obj);
    
    void deleteObject(String className, String id);
    
    List<GedeonDBObject> search(String className,String query);
    
    @PreDestroy
    void cleanUp();
}