package com.vten.gedeon.apiimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.dao.GedCollectionDAO;
import com.vten.gedeon.utils.SaveMode;

public class GedeonCollectionImpl extends PersistableObjectImpl implements GedeonCollection {

	@Autowired
	private GedCollectionDAO collectionDAO;
	
	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_GEDCOLLECTION;
	}
	
	@Override
	public void save(SaveMode mode) {
		if(getPendingEvents().contains(GedEvents.CREATE))
			collectionDAO.createCollection(this);
		else
			collectionDAO.saveObject(this, mode);
	}

}
