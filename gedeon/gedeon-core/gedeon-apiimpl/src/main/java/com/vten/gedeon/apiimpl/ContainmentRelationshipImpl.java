package com.vten.gedeon.apiimpl;

import com.vten.gedeon.api.Containable;
import com.vten.gedeon.api.Container;
import com.vten.gedeon.api.ContainmentRelationship;

public class ContainmentRelationshipImpl extends PersistableObjectImpl implements ContainmentRelationship {

	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Containable getContainable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContainable(Containable containable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getClassName() {
		return ContainmentRelationship.class.getSimpleName();
	}

}
