package com.vten.gedeon.api;

public interface ContainmentRelationship extends PersistableObject {
	
	public Container getContainer();
	public void setContainer(Container container);

	public Containable getContainable();
	public void setContainable(Containable containable);
	
	
}
