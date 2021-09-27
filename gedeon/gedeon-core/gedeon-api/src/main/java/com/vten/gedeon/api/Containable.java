package com.vten.gedeon.api;

import java.util.List;

public interface Containable extends PersistableObject{
	
	public List<ContainmentRelationship> getContainer();
}