package com.vten.gedeon.dao;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.utils.SaveMode;

public interface PersistableObjectDAO {
	
	public default PersistableObject getObject(String className, String id) {
		return getObject(className,id,true);
	}
	
	public PersistableObject getObject(String className, String idOrName, boolean useId);
	
	public void saveObject(PersistableObject obj, SaveMode mode);
	
	public void deleteObject(PersistableObject obj);
	
	public PersistableObject getInstanceByClassName(String className);
}
