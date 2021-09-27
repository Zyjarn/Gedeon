package com.vten.gedeon.dao.connector;

import javax.annotation.PreDestroy;

import com.vten.gedeon.dao.data.OEDBObject;

public interface OEDBConnector
{
    int initDB();
    
    OEDBObject getObject(final String className, final String id);
    
    OEDBObject saveObject(final String className, final OEDBObject obj);
    
    void deleteObject(final String className, final String id);
    
    @PreDestroy
    void cleanUp();
}