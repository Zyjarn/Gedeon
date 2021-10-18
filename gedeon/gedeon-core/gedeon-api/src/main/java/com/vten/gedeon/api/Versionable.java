package com.vten.gedeon.api;

import com.vten.gedeon.api.utils.GedeonProperties;

public interface Versionable extends Containable,Storable{
	
	public void checkin(int majorMinor);
	public void checkout();
	public void cancelCheckout();
	
	public default Integer getMajorVersion() {
		return getProperties().get(GedeonProperties.PROP_MAJOR_VERSION).getIntegerValue();
	}
	
	public default void setMajorVersion(Integer majorVersion) {
		setPropertyValue(GedeonProperties.PROP_MAJOR_VERSION,majorVersion);
	}

	public default Integer getMinorVersion() {
		return getProperties().get(GedeonProperties.PROP_MINOR_VERSION).getIntegerValue();
	}
	public default void setMinorVersion(Integer minorVersion) {
		setPropertyValue(GedeonProperties.PROP_MINOR_VERSION,minorVersion);
	}
	
	public default boolean isCurrentVersion() {
		return getProperties().get(GedeonProperties.PROP_IS_CURRENT_VERSION).getBooleanValue();
	}
	public default void isCurrentVersion(Boolean val) {
		setPropertyValue(GedeonProperties.PROP_IS_CURRENT_VERSION,val);
	}

	public default boolean isReserved() {
		return getProperties().get(GedeonProperties.PROP_IS_RESERVED).getBooleanValue();
	}
	public default void isReserved(boolean val) {
		setPropertyValue(GedeonProperties.PROP_IS_RESERVED,val);
	}
}
