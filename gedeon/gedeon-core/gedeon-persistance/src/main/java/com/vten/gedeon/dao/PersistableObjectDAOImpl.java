package com.vten.gedeon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.OEFactory;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.dao.connector.OEDBConnector;
import com.vten.gedeon.exception.OERuntimeException;
import com.vten.gedeon.utils.SaveMode;

@Service
public class PersistableObjectDAOImpl extends OpenECMDAO implements PersistableObjectDAO{
	
	private OEFactory factory;
	
	@Autowired
	public PersistableObjectDAOImpl(OEDBConnector dbConnect,OEFactory factory) {
		super(dbConnect);
	}

	@Override
	public PersistableObject getObject(String className, String id) {
		PersistableObject persistObj = getInstanceByClassName(className);
		return super.getObject(persistObj, className, id);
	}
	
	@Override
	public void saveObject(PersistableObject obj, SaveMode mode) {
		super.saveObject(obj, mode);
	}
	
	@Override
	public PersistableObject getInstanceByClassName(String className) {
		switch(className) {
		case "OEDocument": return factory.createOEDocument();
		case "OEFolder": return factory.createOEFolder();
		case "ClassDefinition" : return factory.createClassDefinition();
		case "ContainmentRelationship": return factory.createContainmentRelationship();
		case "PropertyTemplate": return factory.createPropertyTemplate();
		default : throw new OERuntimeException(String.format("Unable to instantiate object with className : '%s' ", className));
		}
	}

	@Override
	public void deleteObject(PersistableObject obj) {
		// TODO Auto-generated method stub
		
	}
}
