package com.vten.gedeon.apiimpl;

import java.util.List;

import com.vten.gedeon.api.AbstractPersistableObject;
import com.vten.gedeon.api.ContainmentRelationship;

public abstract class AbstractPersistableObjectImpl extends ContainableImpl implements AbstractPersistableObject{

	@Override
	public List<ContainmentRelationship> getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

}
