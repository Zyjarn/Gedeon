package com.vten.gedeon.api;

import java.time.LocalDateTime;
import java.util.List;

import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.utils.SaveMode;

public interface PersistableObject extends GedObject {

	/**
	 * Save the current instance of object in collection. Parameter allow to decide
	 * if a round trip to the server must be performed to update the document with
	 * data on collection
	 * 
	 * @param mode REFRESH or NO_REFRESH to update current instance with data in
	 *             collection
	 */
	public void save(SaveMode mode);

	/**
	 * Round trip from server to update object data
	 */
	public void refresh();

	/**
	 * Send signal to delete object. Object is not deleted until the save function
	 * is called.
	 */
	public void delete();

	/**
	 * Function use to retrieve the object by dao
	 * 
	 * @return
	 */
	public String getTableName();

	/**
	 * Get value of property 'Name'
	 */
	@Override
	public default String getName() {
		return getProperties().get(GedeonProperties.PROP_NAME).getStringValue();
	}

	/**
	 * Set value of property 'Name'
	 * 
	 * @param name
	 */
	@Override
	public default void setName(String name) {
		setPropertyValue(GedeonProperties.PROP_NAME, name);
	}

	/**
	 * Set value of property 'AddedBy'
	 * 
	 * @param creator
	 */
	public default void setAddedBy(String creator) {
		setPropertyValue(GedeonProperties.PROP_ADDED_BY, creator);
	}

	/**
	 * Get value of property 'AddedBy'
	 * 
	 */
	public default String getAddedBy() {
		return getProperties().get(GedeonProperties.PROP_ADDED_BY).getStringValue();
	}

	/**
	 * Set value of property 'AddedOn'
	 * 
	 * @param creator
	 */
	public default void setAddedOn(LocalDateTime dateCreated) {
		setPropertyValue(GedeonProperties.PROP_DATE_ADDED_ON, dateCreated);
	}

	/**
	 * Get value of property 'AddedOn'
	 * 
	 * @param creator
	 */
	public default LocalDateTime getAddedOn() {
		return getProperties().get(GedeonProperties.PROP_DATE_ADDED_ON).getDateTime();
	}

	/**
	 * Set value of property 'LastModifier'
	 * 
	 * @param creator
	 */
	public default void setLastModifier(String lastModifier) {
		setPropertyValue(GedeonProperties.PROP_LAST_MODIFIER, lastModifier);
	}

	/**
	 * Get value of property 'LastModifier'
	 * 
	 * @param creator
	 */
	public default String getLastModifier() {
		return getProperties().get(GedeonProperties.PROP_LAST_MODIFIER).getStringValue();
	}

	/**
	 * Set value of property 'DateSaved'
	 * 
	 * @param creator
	 */
	public default void setDateSaved(LocalDateTime dateLastModified) {
		setPropertyValue(GedeonProperties.PROP_DATE_SAVED, dateLastModified);
	}

	/**
	 * Get value of property 'DateSaved'
	 * 
	 * @param creator
	 */
	public default LocalDateTime getDateSaved() {
		return getProperties().get(GedeonProperties.PROP_DATE_SAVED).getDateTime();
	}

	/**
	 * Get value of property 'Name'
	 */
	public default GedId getObjectClassId() {
		return getProperties().get(GedeonProperties.PROP_OBJECT_CLASS).getIdValue();
	}

	/**
	 * Set value of property 'Name'
	 * 
	 * @param name
	 */
	public default void setObjectClassId(GedId name) {
		setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS, name);
	}

	/**
	 * Get the current sequence number of the object
	 * 
	 * @return integer value of the sequence number
	 */
	public int getSeqNo();

	/**
	 * Get all the pending events waiting on the object
	 * 
	 * @param list of GedEvents
	 */
	public List<GedEvents> getPendingEvents();

}