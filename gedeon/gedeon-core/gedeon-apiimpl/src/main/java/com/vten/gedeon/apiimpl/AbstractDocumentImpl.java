package com.vten.gedeon.apiimpl;

import java.util.List;

import com.vten.gedeon.api.AbstractDocument;
import com.vten.gedeon.api.ContainmentRelationship;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.utils.SaveMode;

public abstract class AbstractDocumentImpl extends VersionableImpl implements AbstractDocument {

	@Override
	public void checkin(int majorMinor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelCheckout() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ContainmentRelationship> getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(SaveMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(String propertyName, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSeqNo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GedEvents> getPendingEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

}
