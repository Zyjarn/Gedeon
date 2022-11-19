package com.vten.gedeon.apiimpl;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.Storable;
import com.vten.gedeon.api.admin.GedeonClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.connector.content.GedeonContentService;
import com.vten.gedeon.dao.ClassDefinitionDAO;
import com.vten.gedeon.dao.GedCollectionDAO;
import com.vten.gedeon.dao.PersistableObjectDAO;
import com.vten.gedeon.dao.PropertyDefinitionDAO;
import com.vten.gedeon.exception.GedeonIOException;
import com.vten.gedeon.utils.SaveMode;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GedeonCollectionImpl extends PersistableObjectImpl implements GedeonCollection {

	@Autowired
	protected PersistableObjectDAO dao;

	@Autowired
	private GedCollectionDAO collectionDAO;

	@Autowired
	private ClassDefinitionDAO classDefDAO;

	@Autowired
	private PropertyDefinitionDAO propDefDAO;

	@Autowired
	private GedeonContentService contentService;

	@Autowired
	@Getter
	private GedFactory factory;

	@Getter
	private boolean isInit = false;

	public GedeonCollectionImpl(boolean exist) {
		isInit = exist;
	}

	@Override
	public String getTableName() {
		return GedeonProperties.CLASS_GEDCOLLECTION;
	}

	@Override
	public void save(SaveMode mode) {
		if (getPendingEvents().contains(GedEvents.CREATE)) {
			collectionDAO.createCollection(this);
			isInit = false;
		} else {
			collectionDAO.saveObject(this, mode);
		}
	}

	@Override
	public void refresh() {
		isInit = false;
		PersistableObject refreshObject;
		if (getId() != null) {
			refreshObject = getObject(getId(), getTableName());
		} else {
			refreshObject = dao.getObject(this, getTableName(), getName(), false);
		}
		setProperties(refreshObject.getProperties());
		setSeqNo(refreshObject.getSeqNo());
	}

	@Override
	public void saveObject(PersistableObject object, Class<? extends PersistableObject> clazz, SaveMode mode) {
		dao.saveObject(object, mode);
	}

	@Override
	public PersistableObject refreshObject(PersistableObject object) {
		return dao.getObject(this, object.getTableName(), object.getId().toString());
	}

	@Override
	public PersistableObject getObject(GedId id, String tableName) {
		return dao.getObject(this, tableName, id.toString());
	}

	@Override
	public PersistableObject getObject(String name, String tableName) {
		return dao.getObject(this, tableName, name, false);
	}

	@Override
	public InputStream getContent(Storable storableObject) throws GedeonIOException {
		return contentService.getContent(storableObject.getId(), storableObject.getStorage());
	}

	@Override
	public PropertyDefinition newCopyInstance(PropertyDefinition propertyDefinition) {
		// TODO put object creation in gedeoncollection?
		PropertyDefinition copyObject = factory.createPropertyDefinition(propertyDefinition.getGedeonCollection(),
				propertyDefinition.getPropertyTemplate().getName());
		copyObject.setDefaultValue(propertyDefinition.getDefaultValue());
		copyObject.setDisplayName(propertyDefinition.getDisplayName());
		copyObject.setName(propertyDefinition.getName());
		copyObject.setSetability(propertyDefinition.getSetability());
		copyObject.isRequired(propertyDefinition.isRequired());
		copyObject.setPropertyTemplateId(propertyDefinition.getPropertyTemplateId());
		return copyObject;
	}

	@Override
	public List<PropertyDefinition> getPropertiesDefinitionForClass(GedeonClassDefinition classDef) {
		return propDefDAO.getPropertiesDefinitionForClass(this, classDef);
	}

	@Override
	public PropertyDefinition getPropertyDefinition(GedId id) {
		return propDefDAO.getObject(getGedeonCollection(), id);
	}

	@Override
	public GedeonClassDefinition getClassDefinition(GedId id) {
		return classDefDAO.getObject(this, id);
	}

	@Override
	public GedeonClassDefinition getClassDefinition(String name) {
		return classDefDAO.getObject(this, name);
	}

	@Override
	public void saveStorage(Storage storage, SaveMode mode) {
		saveObject(storage, mode);
		try {
			contentService.initStorage(storage);
		} catch (GedeonIOException e) {
			// TODO delete storage
		}
	}

}
