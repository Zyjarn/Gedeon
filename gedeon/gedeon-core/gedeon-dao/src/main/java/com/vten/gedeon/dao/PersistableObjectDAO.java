package com.vten.gedeon.dao;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.utils.SaveMode;

public interface PersistableObjectDAO {
	
	public default PersistableObject getObject(GedeonCollection collection,String className, String id) {
		return getObject(collection,className,id,true);
	}
	
	public PersistableObject getObject(GedeonCollection collection,String className, String idOrName, boolean useId);
	
	public void saveObject(PersistableObject obj, SaveMode mode);
	
	public void deleteObject(String className, PersistableObject obj);
	
}
