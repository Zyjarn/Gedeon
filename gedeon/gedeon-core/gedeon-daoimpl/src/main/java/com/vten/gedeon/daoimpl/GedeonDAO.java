package com.vten.gedeon.daoimpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.apiimpl.PersistableObjectImpl;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.ClassDefinitionDAO;
import com.vten.gedeon.dao.GedCollectionDAO;
import com.vten.gedeon.dao.PersistableObjectDAO;
import com.vten.gedeon.dao.PropertyDefinitionDAO;
import com.vten.gedeon.dao.PropertyTemplateDAO;
import com.vten.gedeon.dao.data.GedeonDBObject;
import com.vten.gedeon.daoimpl.validation.SaveValidator;
import com.vten.gedeon.model.property.PropertyType;
import com.vten.gedeon.utils.SaveMode;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GedeonDAO {

	@Autowired
	private ClassDefinitionDAO classDefDAO;
	@Autowired
	private GedCollectionDAO gedCollectionDAO;
	@Autowired
	private PersistableObjectDAO persistableDAO;
	@Autowired
	private PropertyDefinitionDAO propDefDAO;
	@Autowired
	private PropertyTemplateDAO propTpltDAO;

	@Autowired
	protected GedeonDBConnector connector;
	protected SaveValidator validateSave;

	public PersistableObject getObject(GedeonCollection collection, GedId id,
			Class<? extends PersistableObject> clazz) {
		PersistableObjectImpl instance = null;
		try {
			Constructor<? extends PersistableObject> construct = clazz.getConstructor();
			instance = (PersistableObjectImpl) construct.newInstance();

			connector.getObject(collection.getName(), instance.getClassName(), id.toString());

		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}

	public PersistableObject getObject(GedeonCollection collection, GedId id, String className) {
		PersistableObject instance = null;
		try {
			Class<?> clazz = Class.forName(className);

			instance = getObject(collection, id, clazz.asSubclass(PersistableObject.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return instance;
	}

	public void saveObject(PersistableObject object, SaveMode mode) {

	}

	/**
	 * Fill the given persistable object with the given database object
	 * 
	 * @param instance
	 * @param persistObject
	 * @return
	 */
	protected PersistableObject fillPersistableObjectInstance(PersistableObject instance,
			GedeonDBObject persistObject) {
		Properties props = new PropertiesImpl();
		// Add Id
		props.add(new Property(GedeonProperties.PROP_ID, new GedId(persistObject.getId())));
		// Add class id
		instance.setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS,
				new GedId((String) persistObject.getMapData().get(GedeonProperties.PROP_OBJECT_CLASS)));

		// Retrieve classDefinition if not instance of ClassDefinition class definition
		ClassDefinition classDef = null;
		if ((instance instanceof ClassDefinition) && StringUtils.equals(GedeonProperties.CLASS_CLASSDEFINITION,
				(String) persistObject.getMapData().get(GedeonProperties.PROP_NAME))) {
			classDef = instance.getClassDefinition();
		}

		// Fill properties
		for (Map.Entry<String, Object> e : persistObject.getMapData().entrySet()) {
			props.add(getProperty(instance.getGedeonCollection(), classDef, e.getKey(), e.getValue()));
		}
		// Set properties on instance
		instance.setProperties(props);
		// Set seqNo
		((PersistableObjectImpl) instance).setSeqNo(persistObject.getSeqNo());
		return instance;
	}

	/**
	 * 
	 * @param classDef
	 * @param key
	 * @param value
	 * @return
	 */
	protected Property getProperty(GedeonCollection collection, ClassDefinition classDef, String key, Object value) {
		Property prop = new Property();
		prop.setSymbolicName(key);
		prop.setObjectValue(value);

		if (classDef != null) {
			PropertyDefinition propDef = classDef.getPropertiesDefinitions().get(key);
			switch (propDef.getPropertyTemplate().getType()) {
			case ID:
				prop.setObjectValue(new GedId((String) value));
				break;
			case OBJECTID: {
				String[] args = StringUtils.split((String) value);
				String className = args[0];
				GedId id = new GedId(args[1]);
				prop.setObjectValue(getObject(collection, id, className));
			}

				break;
			case DATE:
			case STRING:
			case BOOLEAN:
			case DOUBLE:
			case INTEGER:
			case LONG:
				prop.setObjectValue(value);
				break;
			default:
				break;

			}
			if (PropertyType.ID.equals(propDef.getPropertyTemplate().getType())) {

			}
		}

		return prop;
	}

}
