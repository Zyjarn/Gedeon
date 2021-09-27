package com.vten.gedeon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.ClassDefinition;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.apiimpl.OEFactoryImpl;
import com.vten.gedeon.dao.connector.OEDBConnector;
import com.vten.gedeon.utils.SaveMode;

@Service
public class ClassDefinitionDAOImpl extends OpenECMDAO implements ClassDefinitionDAO{
	
	private OEFactoryImpl factory;
	
	@Autowired
	public ClassDefinitionDAOImpl(OEDBConnector dbConnect,OEFactoryImpl factory) {
		super(dbConnect);
	}

	@Override
	public ClassDefinition getObject(String className, String id) {
		PersistableObject persistObj = getInstanceByClassName(className);
		return (ClassDefinition) super.getObject(persistObj, className, id);
	}
	
	@Override
	public void saveObject(ClassDefinition obj, SaveMode mode) {
		super.saveObject(obj, mode);
	}
	
	@Override
	public ClassDefinition getInstanceByClassName(String className) {
		return factory.createClassDefinition();
	}

	@Override
	public void deleteObject(ClassDefinition obj) {
		// TODO Auto-generated method stub
		
	}
}
