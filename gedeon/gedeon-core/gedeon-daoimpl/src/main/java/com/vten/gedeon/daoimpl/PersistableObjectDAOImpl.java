package com.vten.gedeon.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.PersistableObjectDAO;

@Service
public class PersistableObjectDAOImpl extends GedeonDAO implements PersistableObjectDAO {

	@Autowired
	public PersistableObjectDAOImpl(GedeonDBConnector dbConnect, GedFactory factory) {
		super(dbConnect, factory);
	}

	@Override
	public PersistableObject getObject(String className, String idOrName, boolean useId) {
		return useId ? super.getObjectById(this.getInstanceByClassName(className), className, idOrName)
				: super.getObjectByName(className, idOrName);
	}

	@Override
	public void deleteObject(String className, PersistableObject obj) {
		connector.deleteObject(className, obj.getId().getValue());
	}
}