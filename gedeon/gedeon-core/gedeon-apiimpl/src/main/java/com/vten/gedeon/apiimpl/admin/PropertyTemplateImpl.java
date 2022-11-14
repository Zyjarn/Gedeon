package com.vten.gedeon.apiimpl.admin;

import org.springframework.stereotype.Component;

import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;

@Component
public class PropertyTemplateImpl extends PersistableObjectImpl implements PropertyTemplate {

	@Override
	public String getClassName() {
		return GedeonProperties.CLASS_PROPERTYTEMPLATE;
	}

	@Override
	public void refresh() {

		// Refresh object by id or name
		PersistableObjectImpl refreshObject = (PersistableObjectImpl) ((getId() == null) || getId().isBlank()
				? getGedeonCollection().getObject(getTableName(), getName())
				: getGedeonCollection().getObject(getTableName(), getId().getValue()));
		this.setProperties(refreshObject.getProperties());
		setId(refreshObject.getId());
		setSeqNo(refreshObject.getSeqNo());
	}

	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_PROPERTYTEMPLATE;
	}
}
