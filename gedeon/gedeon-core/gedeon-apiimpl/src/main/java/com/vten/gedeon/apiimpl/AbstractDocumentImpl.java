package com.vten.gedeon.apiimpl;

import java.util.List;

import com.vten.gedeon.api.AbstractDocument;
import com.vten.gedeon.api.ContainmentRelationship;

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

}
