package com.vten.gedeon.daoimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.admin.PropertyDefinitionImpl;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.PropertyDefinitionDAO;
import com.vten.gedeon.utils.SaveMode;

@Service
public class PropertyDefinitionDAOImpl extends GedeonDAO implements PropertyDefinitionDAO {

	@Autowired
	public PropertyDefinitionDAOImpl(GedeonDBConnector dbConnect, GedFactory factory) {
		super(dbConnect, factory);
	}

	@Cacheable(cacheNames = "PropertyDefinitionId", key = "#id.value")
	@Override
	public PropertyDefinition getObject(GedId id) {
		//Retrieve object
		PropertyDefinitionImpl obj = (PropertyDefinitionImpl) super.getObjectById(new PropertyDefinitionImpl(),
				GedeonProperties.CLASS_PROPERTYDEFINITION, id.getValue());
		//Retrieve associated property template
		if (obj.getProperties().containsProperty(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID))
			obj.setPropertyTemplate(factory.getPropertyTemplate(
					obj.getProperties().get(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID).getIdValue()));
		return obj;
	}

	@Override
	public void deleteObject(PropertyDefinition obj) {
		connector.deleteObject(GedeonProperties.CLASS_PROPERTYDEFINITION, obj.getId().getValue());
	}

	@CacheEvict(cacheNames = "PropertyDefinitionId", key = "#obj.getId")
	@Override
	public void saveObject(PropertyDefinition obj, SaveMode mode) {
		super.saveObject(obj, mode);
	}

	@Override
	public List<PropertyDefinition> getPropertiesDefinitionForClass(ClassDefinition classDef) {
		GedSearch searchByName = new GedSearch.SearchBuilder().select().from(GedeonProperties.CLASS_PROPERTYDEFINITION)
				.where().equals(GedeonProperties.PROP_PARENT_CLASS_ID, classDef.getId()).build(factory);
		List<PersistableObject> results = searchByName.search();
		return results.stream().map(PropertyDefinition.class::cast).collect(Collectors.toList());
	}

	@Override
	public PropertyDefinition newCopyInstance(PropertyDefinition propertyDefinition) {
		PropertyDefinition copyObject = factory.createPropertyDefinition();
		copyObject.setDefaultValue(propertyDefinition.getDefaultValue());
		copyObject.setDisplayName(propertyDefinition.getDisplayName());
		copyObject.setName(propertyDefinition.getName());
		copyObject.setPropertyTemplate(propertyDefinition.getPropertyTemplate());
		copyObject.setSetability(propertyDefinition.getSetability());
		return copyObject;
	}
}
