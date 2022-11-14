package com.vten.gedeon.daoimpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.PersistableObjectDAO;
import com.vten.gedeon.daoimpl.validation.SaveValidator;

@Service
public class PersistableObjectDAOImpl extends GenericGedeonDAO<PersistableObject> implements PersistableObjectDAO {

	private static final List<Class<? extends PersistableObject>> MANAGED_CLASS = Arrays
			.asList(PersistableObject.class);

	@Autowired
	public PersistableObjectDAOImpl(GedeonDBConnector dbConnect, GedFactory factory, SaveValidator saveValidator) {
		super(dbConnect, factory, saveValidator);
	}

	@Override
	protected List<Class<? extends PersistableObject>> getManagedClass() {
		return MANAGED_CLASS;
	}

	@Override
	public PersistableObject getObject(GedeonCollection collection, String className, String idOrName, boolean useId) {
		return useId ? super.getObjectById(this.getInstanceByClassName(collection, className), className, idOrName)
				: super.getObjectByName(collection, className, idOrName);
	}

	@Override
	public void deleteObject(String className, PersistableObject obj) {
		connector.deleteObject(obj.getGedeonCollection().getName(), className, obj.getId().getValue());
	}

	@Override
	protected PersistableObject getInstance(GedeonCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}
}