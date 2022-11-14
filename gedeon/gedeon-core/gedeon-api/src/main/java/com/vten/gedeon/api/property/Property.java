package com.vten.gedeon.api.property;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.vten.gedeon.api.list.StringList;
import com.vten.gedeon.api.utils.GedId;

import lombok.Getter;
import lombok.Setter;

public class Property {

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
	@Getter
	@Setter
	String symbolicName;

	/**
	 * Simple constructor for empty property
	 */
	public Property() {
		// Nothing to do
	}

	/**
	 * Simple constructor to set values directly
	 * 
	 * @param propertyName
	 * @param propertyValue
	 */
	public Property(String propertyName, Object propertyValue) {
		setSymbolicName(propertyName);
		setObjectValue(propertyValue);
	}

	public Object getObjectValue() {
		return value;
	}

	public String getStringValue() {
		return (String) value;
	}

	public StringList getListStringValue() {
		return (StringList) value;
	}

	public Integer getIntegerValue() {
		return (Integer) value;
	}

	@SuppressWarnings("unchecked")

	public List<Integer> getListIntegerValue() {
		// TODO improve and throw exception
		return (List<Integer>) (List<?>) listValue;
	}

	public GedId getIdValue() {
		return (GedId) value;
	}

	public List<GedId> getListIdValue() {
		return Arrays.asList((GedId[]) value);
	}

	public Boolean getBooleanValue() {
		return (Boolean) value;
	}

	@SuppressWarnings("unchecked")
	public List<Boolean> getListBooleanValue() {
		return (List<Boolean>) (List<?>) value;
	}

	public LocalDateTime getDateTime() {
		return (LocalDateTime) value;
	}

	public void setObjectValue(Object obj) {
		value = obj;
	}

	public void getDoubleValue() {
		// TODO Auto-generated method stub

	}

	public void getListDoubleValue() {
		// TODO Auto-generated method stub

	}

	public void getListLongValue() {
		// TODO Auto-generated method stub

	}

	public void getLongValue() {
		// TODO Auto-generated method stub

	}

}
