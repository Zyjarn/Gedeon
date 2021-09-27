package com.vten.gedeon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.apiimpl.OEFactoryImpl;
import com.vten.gedeon.dao.connector.OEDBConnector;
import com.vten.gedeon.exception.OERuntimeException;

@Service
public class PersistableObjectDAOImpl extends OpenECMDAO implements PersistableObjectDAO
{
    private OEFactoryImpl factory;
    
    @Autowired
    public PersistableObjectDAOImpl(final OEDBConnector dbConnect, final OEFactoryImpl factory) {
        super(dbConnect);
    }
    
    public PersistableObject getObject(final String className, final String idOrName,boolean useId) {
        final PersistableObject persistObj = this.getInstanceByClassName(className);
        return useId ? super.getObjectById(persistObj, className, idOrName):
        	super.getObjectByName(persistObj, className, idOrName);
    }
    
    
    @Override
    public PersistableObject getInstanceByClassName(final String className) {
        switch (className) {
            case "OEDocument": {
                return (PersistableObject)this.factory.createOEDocument();
            }
            case "PropertyTemplate": {
                return (PersistableObject)this.factory.createPropertyTemplate();
            }
            case "ClassDefinition": {
                return (PersistableObject)this.factory.createClassDefinition();
            }
            case "OEFolder": {
                return (PersistableObject)this.factory.createOEFolder();
            }
            case "ContainmentRelationship": {
                return (PersistableObject)this.factory.createContainmentRelationship();
            }
            default:
                break;
        }
        throw new OERuntimeException(String.format("Unable to instantiate object with className : '%s' ", className));
    }
    
    public void deleteObject(final PersistableObject obj) {
    }
}