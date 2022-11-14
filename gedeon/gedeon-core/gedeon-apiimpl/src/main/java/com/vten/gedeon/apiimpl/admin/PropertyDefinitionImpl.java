package com.vten.gedeon.apiimpl.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;
import com.vten.gedeon.dao.ClassDefinitionDAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PropertyDefinitionImpl extends PersistableObjectImpl implements PropertyDefinition {

	@Autowired
	private ClassDefinitionDAO classDefDAO;

	@Getter
	private PropertyTemplate propertyTemplate;

	@Setter
	private ClassDefinition associatedClass;

	@Getter
	@Setter
	private boolean inherited;

	public PropertyDefinitionImpl(PropertyTemplate template) {
		setPropertyTemplate(template);
	}

	@Override
	public ClassDefinition getAssociatedClass() {
		if ((associatedClass == null) && getProperties().containsProperty(GedeonProperties.PROP_PARENT_CLASS_ID)) {
			associatedClass = classDefDAO.getObject(getGedeonCollection(),
					getProperties().get(GedeonProperties.PROP_PARENT_CLASS_ID).getIdValue());
		} else {
			// throw runtime not in cache
		}

		return associatedClass;
	}

	@Override
	public String getClassName() {
		// TODO remove and try to put a custom class def for first save
		return GedeonProperties.CLASS_PROPERTYDEFINITION;
	}

	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_PROPERTYDEFINITION;
	}

	@Override
	public void setPropertyTemplate(PropertyTemplate propertyTemplate) {
		this.propertyTemplate = propertyTemplate;
		setPropertyTemplateId(propertyTemplate.getId());
	}

}
