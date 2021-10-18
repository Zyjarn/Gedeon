package com.vten.gedeon.apiimpl.property;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.vten.gedeon.api.list.StringList;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedId;

import lombok.Getter;
import lombok.Setter;

public class PropertyImpl implements Property{
	
	/**
	 * Value of the property in case of single valued property
	 */
	Object value;
	/**
	 * Value of the property in case of multi valued property
	 */
	List<Object> listValue = null;
	/**
	 * Symbolic name of the property that match a property definition
	 */
	@Getter @Setter
	String symbolicName;
	
	/**
	 * Simple constructor for empty property
	 */
	public PropertyImpl() {
		//Nothing to do
	}
	
	/**
	 * Simple constructor to set values directly
	 * @param propertyName
	 * @param propertyValue
	 */
	public PropertyImpl(String propertyName, Object propertyValue) {
		setSymbolicName(propertyName);
		setObjectValue(propertyValue);
	}

	@Override
	public Object getObjectValue() {
		return value;
	}

	@Override
	public String getStringValue() {
		return (String) value;
	}

	@Override
	public StringList getListStringValue() {
		return (StringList) value;
	}

	@Override
	public Integer getIntegerValue() {
		return (Integer) value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getListIntegerValue() {
		//TODO improve and throw exception
		return (List<Integer>) (List<?>) listValue;
	}

	@Override
	public GedId getIdValue() {
		return (GedId) value;
	}

	@Override
	public List<GedId> getListIdValue() {
		return Arrays.asList((GedId[]) value);
	}

	@Override
	public Boolean getBooleanValue() {
		return (Boolean) value;
	}

	@Override
	public LocalDateTime getDateTime() {
		return (LocalDateTime) value;
	}

	@Override
	public void setObjectValue(Object obj) {
		value = obj;
	}

}
