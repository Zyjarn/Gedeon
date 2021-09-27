package com.vten.gedeon.apiimpl.property;

import org.springframework.stereotype.Component;

import com.vten.gedeon.api.property.PropertyTemplate;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;

@Component
public class PropertyTemplateImpl extends PersistableObjectImpl implements PropertyTemplate{
	
	@Override
	public String getClassName() {
		return PropertyTemplate.class.getSimpleName();
	}

	@Override
	public void refresh() {
		PersistableObjectImpl refreshObject = (PersistableObjectImpl) dao.getObject(getClassName(), getName());
		this.setProperties(refreshObject.getProperties());
		setSeqNo(refreshObject.getSeqNo());
	}

}
