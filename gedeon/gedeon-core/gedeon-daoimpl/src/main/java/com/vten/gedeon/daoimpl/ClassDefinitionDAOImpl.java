package com.vten.gedeon.daoimpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.ClassDefinitionDAO;
import com.vten.gedeon.daoimpl.validation.SaveValidator;
import com.vten.gedeon.utils.SaveMode;

@Service
public class ClassDefinitionDAOImpl extends GenericGedeonDAO<ClassDefinition> implements ClassDefinitionDAO {

	private static final List<Class<? extends PersistableObject>> MANAGED_CLASS = Arrays.asList(ClassDefinition.class);

	@Autowired
	public ClassDefinitionDAOImpl(GedeonDBConnector dbConnect, GedFactory factory, SaveValidator saveValidator) {
		super(dbConnect, factory, saveValidator);
	}

	@Override
	protected List<Class<? extends PersistableObject>> getManagedClass() {
		return MANAGED_CLASS;
	}

	@Cacheable(cacheNames = "ClassDefinitionName", key = "{#collection.symbolicName , #name}")
	@Override
	public ClassDefinition getObject(GedeonCollection collection, String name) {
		return (ClassDefinition) super.getObjectByName(collection, GedeonProperties.CLASS_CLASSDEFINITION, name);
	}

	@Cacheable(cacheNames = "ClassDefinitionId", key = "#id.value")
	@Override
	public ClassDefinition getObject(GedeonCollection collection, GedId id) {
		ClassDefinition classDef = super.getObjectById(factory.createClassDefinition(collection),
				GedeonProperties.CLASS_CLASSDEFINITION, id.getValue());
		if (classDef.getProperties().containsProperty(GedeonProperties.PROP_PROPERTIES_DEFINITION)) {
			classDef.getPropertiesDefinitions().addAll(collection.getPropertiesDefinitionForClass(classDef));
		}
		return classDef;
	}

	@Override
	public void deleteObject(ClassDefinition obj) {
		connector.deleteObject(obj.getGedeonCollection().getName(), obj.getClassName(), obj.getId().getValue());
	}

	@Caching(evict = { @CacheEvict(cacheNames = "ClassDefinitionId", key = "#obj.getId"),
			@CacheEvict(cacheNames = "ClassDefinitionName", key = "#obj.getName") })
	@Override
	public void saveObject(ClassDefinition obj, SaveMode mode) {
		// TODO more secure
		if (!obj.isSystem()) {
			// TODO check classDefinition and properties definition
		}

		super.saveObject(obj, mode);
		// TODO save properties definition in same transaction
		obj.getPropertiesDefinitions().save();
	}

	@Override
	protected ClassDefinition getInstance(GedeonCollection collection) {
		return factory.createClassDefinition(collection);
	}

}
