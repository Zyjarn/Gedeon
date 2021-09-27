package com.vten.gedeon.apiimpl;

import java.util.List;

import com.vten.gedeon.api.Containable;
import com.vten.gedeon.api.Container;
import com.vten.gedeon.api.ContainmentRelationship;

public abstract class ContainerImpl extends AbstractPersistableObjectImpl implements Container {

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

	@Override
	public void files(Containable containable) {
		// TODO Auto-generated method stub
		
	}

}
