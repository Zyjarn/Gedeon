package com.vten.gedeon.api;

import java.time.LocalDateTime;
import java.util.List;

import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.OEConstants;
import com.vten.gedeon.utils.SaveMode;

public interface PersistableObject extends OEObject{
	
	public void save(SaveMode mode);
	public void refresh();
	
	public void setPropertyValue(String propertyName, Object value);
	
	@Override
	public default String getName() {
		return getProperties().get(OEConstants.PROP_NAME).getStringValue();
	}
	
	@Override
	public default void setName(String name) {
		setPropertyValue(OEConstants.PROP_NAME,name);
	}
	
	/**
	 * Set value of property 'AddedBy'
	 * @param creator
	 */
	public default void setAddedBy(String creator){
		setPropertyValue(OEConstants.PROP_ADDED_BY,creator);
	}
	
	/**
	 * Get value of property 'AddedBy'
	 * @param creator
	 */
	public default String getAddedBy() {
		return getProperties().get(OEConstants.PROP_ADDED_BY).getStringValue();
	}
	
	/**
	 * Set value of property 'AddedOn'
	 * @param creator
	 */
	public default void setAddedOn(LocalDateTime dateCreated){
		setPropertyValue(OEConstants.PROP_DATE_ADDED_ON,dateCreated);
	}
	
	/**
	 * Get value of property 'AddedOn'
	 * @param creator
	 */
	public default LocalDateTime getAddedOn() {
		return getProperties().get(OEConstants.PROP_DATE_ADDED_ON).getDateTime();
	}
	
	/**
	 * Set value of property 'LastModifier'
	 * @param creator
	 */
	public default void setLastModifier(String lastModifier){
		setPropertyValue(OEConstants.PROP_LAST_MODIFIER,lastModifier);
	}
	
	/**
	 * Get value of property 'LastModifier'
	 * @param creator
	 */
	public default String getLastModifier(){
		return getProperties().get(OEConstants.PROP_LAST_MODIFIER).getStringValue();
	}
	
	/**
	 * Set value of property 'DateSaved'
	 * @param creator
	 */
	public default void setDateSaved(LocalDateTime dateLastModified){
		setPropertyValue(OEConstants.PROP_DATE_SAVED,dateLastModified);
	}
	
	/**
	 * Get value of property 'DateSaved'
	 * @param creator
	 */
	public default LocalDateTime getDateSaved(){
		return getProperties().get(OEConstants.PROP_DATE_SAVED).getDateTime();
	}
	
	/**
	 * Get the current sequence number of the object
	 * @return integer value of the sequence number
	 */
	public int getSeqNo();
	
	/**
	 * Get all the pending events waiting on the object 
	 * @param list of GedEvents
	 */
	public List<GedEvents> getPendingEvents();
	
}