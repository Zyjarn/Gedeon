package com.vten.gedeon.dao.connector;

import javax.annotation.PreDestroy;

import com.vten.gedeon.dao.data.GedeonDBObject;

public interface GedeonDBConnector
{
    int initDB();
    
    GedeonDBObject getObject(final String className, final String id);
    
    GedeonDBObject saveObject(final String className, final GedeonDBObject obj);
    
    void deleteObject(final String className, final String id);
    
    @PreDestroy
    void cleanUp();
}