package com.vten.gedeon.daoimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.GedeonClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.admin.PropertyDefinitionImpl;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.PropertyDefinitionDAO;
import com.vten.gedeon.daoimpl.validation.SaveValidator;
import com.vten.gedeon.utils.SaveMode;

@Service
public class PropertyDefinitionDAOImpl extends GenericGedeonDAO<PropertyDefinition> implements PropertyDefinitionDAO {

	@Autowired
	public PropertyDefinitionDAOImpl(GedeonDBConnector dbConnect, GedFactory factory, SaveValidator saveValidator) {
		super(dbConnect, factory, saveValidator);
	}

	@Cacheable(cacheNames = "PropertyDefinitionId", key = "#id.value")
	@Override
	public PropertyDefinition getObject(GedeonCollection collection, GedId id) {
		// Retrieve object
		PropertyDefinitionImpl obj = (PropertyDefinitionImpl) super.getObjectById(new PropertyDefinitionImpl(),
				GedeonProperties.CLASS_PROPERTYDEFINITION, id.getValue());
		// Retrieve associated property template
		if (obj.getProperties().containsProperty(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID)) {
			obj.setPropertyTemplate(factory.getPropertyTemplate(collection,
					obj.getProperties().get(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID).getIdValue()));
		}
		return obj;
	}

	@Override
	public void deleteObject(PropertyDefinition obj) {
		connector.deleteObject(obj.getGedeonCollection().getName(), GedeonProperties.CLASS_PROPERTYDEFINITION,
				obj.getId().getValue());
	}

	@CacheEvict(cacheNames = "PropertyDefinitionId", key = "#obj.getId")
	@Override
	public void saveObject(PropertyDefinition obj, SaveMode mode) {
		super.saveObject(obj, mode);
	}

	@Override
	public List<PropertyDefinition> getPropertiesDefinitionForClass(GedeonCollection collection,
			GedeonClassDefinition classDef) {
		GedSearch searchByName = new GedSearch.SearchBuilder().selectAll()
				.from(GedeonProperties.CLASS_PROPERTYDEFINITION).where()
				.equals(GedeonProperties.PROP_PARENT_CLASS_ID, classDef.getId()).build(collection);
		List<PersistableObject> results = searchByName.search();

		List<PropertyDefinition> propertiesDefinition = results.stream().map(PropertyDefinition.class::cast)
				.collect(Collectors.toList());

		// Retrieve associated property template
		propertiesDefinition.forEach(obj -> {
			if (obj.getProperties().containsProperty(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID)) {
				obj.setPropertyTemplate(factory.getPropertyTemplate(collection,
						obj.getProperties().get(GedeonProperties.PROP_PROPERTY_TEMPLATE_ID).getIdValue().getValue()));
			}
		});

		return propertiesDefinition;
	}

	@Override
	protected PropertyDefinition getInstance(GedeonCollection collection) {
		// TODO change to factory?
		return new PropertyDefinitionImpl();
	}

}
