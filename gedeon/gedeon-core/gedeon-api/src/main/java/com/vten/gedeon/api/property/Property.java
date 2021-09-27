package com.vten.gedeon.api.property;

import java.time.LocalDateTime;
import java.util.List;

import com.vten.gedeon.api.list.StringList;
import com.vten.gedeon.api.utils.OEId;

public interface Property {
	
	public String getSymbolicName();
	
	public Object getObjectValue();
	
	public String getStringValue();
	
	public StringList getListStringValue();
	
	public Integer getIntegerValue();
	
	public List<Integer> getListIntegerValue();
	
	public OEId getIdValue();
	
	public List<OEId> getListIdValue();
	
	public Boolean getBooleanValue();
	
	public LocalDateTime getDateTime();
	
	public void setObjectValue(Object obj);
	

	
}
