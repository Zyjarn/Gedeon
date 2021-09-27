package com.vten.gedeon.apiimpl;

import java.util.List;

import com.vten.gedeon.api.Containable;
import com.vten.gedeon.api.ContainmentRelationship;

public abstract class ContainableImpl extends PersistableObjectImpl implements Containable {

	@Override
	public List<ContainmentRelationship> getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

}
