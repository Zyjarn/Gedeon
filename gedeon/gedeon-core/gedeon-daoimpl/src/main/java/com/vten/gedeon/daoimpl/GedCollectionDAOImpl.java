package com.vten.gedeon.daoimpl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.api.utils.Setability;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.GedCollectionDAO;
import com.vten.gedeon.utils.SaveMode;

@Service
public class GedCollectionDAOImpl extends GedeonDAO implements GedCollectionDAO{
	
	private static final Logger LOG = LoggerFactory.getLogger(GedCollectionDAOImpl.class);
	
	public static final String ELT_PROPERTIES_DEFINITIONS = "PropertiesDefinitions";

	@Value("classpath:gedeon.json")
	private Resource resource;
	
	@Autowired
    public GedCollectionDAOImpl(GedeonDBConnector dbConnect, GedFactory factory) {
        super(dbConnect,factory);
    }

	@Override
	public GedeonCollection getObject(GedId id) {
		return (GedeonCollection) super.getObjectById(factory.createGedCollection(),
				GedeonProperties.CLASS_GEDCOLLECTION, id.getValue());
	}

	@Override
	public GedeonCollection getObject(String name) {
		return (GedeonCollection) 
				super.getObjectByName(GedeonProperties.CLASS_GEDCOLLECTION, name);
	}
	
	private String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

	@Override
	public void createCollection(GedeonCollection obj) {
			LOG.info("Init creation of new collection : {}",obj.getName());
			JSONObject jsonObj = new JSONObject(asString(resource));
			//Init Id for
			Map<String, GedId> idObjects = new HashMap<>();
			idObjects.put(GedeonProperties.CLASS_ABSTRACTPERSISTABLEOBJECT, 
					GedId.newIdFromValue(GedeonProperties.CLASS_ABSTRACTPERSISTABLEOBJECT.getBytes()));
			idObjects.put(GedeonProperties.CLASS_ABSTRACTDOCUMENT, 
					GedId.newIdFromValue(GedeonProperties.CLASS_ABSTRACTDOCUMENT.getBytes()));
			idObjects.put(GedeonProperties.CLASS_PROPERTYTEMPLATE, 
					GedId.newIdFromValue(GedeonProperties.CLASS_PROPERTYTEMPLATE.getBytes()));
			idObjects.put(GedeonProperties.CLASS_CLASSDEFINITION, 
					GedId.newIdFromValue(GedeonProperties.CLASS_CLASSDEFINITION.getBytes()));
			idObjects.put(GedeonProperties.CLASS_GEDCOLLECTION, 
					GedId.newIdFromValue(GedeonProperties.CLASS_GEDCOLLECTION.getBytes()));
			idObjects.put(GedeonProperties.CLASS_PROPERTYDEFINITION, 
					GedId.newIdFromValue(GedeonProperties.CLASS_PROPERTYDEFINITION.getBytes()));
			
			//Set generated object class id of GedeonCollection class definition
			obj.setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS,idObjects.get(GedeonProperties.CLASS_GEDCOLLECTION));
			
			if(jsonObj.has(GedeonProperties.CLASS_PROPERTYTEMPLATE)) {
				createSystemPropertyTemplate(idObjects,
						jsonObj.getJSONArray(GedeonProperties.CLASS_PROPERTYTEMPLATE));
				
			}
			//TODO in case of errors, remove all created objects
			
			if(jsonObj.has(GedeonProperties.CLASS_CLASSDEFINITION)) {
				createSystemClassDefinitions(idObjects,
						jsonObj.getJSONArray(GedeonProperties.CLASS_CLASSDEFINITION));
					
			}
			super.saveObject(obj, SaveMode.REFRESH);

	}
	
	protected void createSystemPropertyTemplate(Map<String, GedId> idObjects,JSONArray listPropTplt) {
		JSONObject propTpltOpt;
		for(int i = 0; i < listPropTplt.length(); i++) {
			propTpltOpt = listPropTplt.getJSONObject(i);
			PropertyTemplate propTemplate = factory.createPropertyTemplate();
			propTemplate.setName(propTpltOpt.getString(GedeonProperties.PROP_NAME));
			LOG.info("handle PropertyTemplate : {}",propTemplate.getName());
			propTemplate.setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS, 
					idObjects.get(GedeonProperties.CLASS_PROPERTYTEMPLATE));
			propTemplate.setType(propTpltOpt.getString(GedeonProperties.PROP_PROPERTY_TYPE));
			propTemplate.isList(propTpltOpt.getBoolean(GedeonProperties.PROP_IS_LIST));
			propTemplate.setIsSystem(propTpltOpt.getBoolean(GedeonProperties.PROP_IS_SYSTEM));
			propTemplate.save(SaveMode.NO_REFRESH);
		}
	}
	
	protected void createSystemPropertyDefinition(Map<String, GedId> idObjects,JSONArray listPropTplt,ClassDefinition classDef) {
		JSONObject propDefOpt;
		for(int j = 0; j < listPropTplt.length(); j++) {
			propDefOpt = listPropTplt.getJSONObject(j);
			//Create property definition with name of associated entry template
			String propertyTemplate = propDefOpt.getString(GedeonProperties.CLASS_PROPERTYTEMPLATE);
			PropertyDefinition propDef = factory.createPropertyDefinition(propertyTemplate);
			propDef.setName(propertyTemplate);
			LOG.info("handle PropertyDefinition : {}",propDef.getName());
			propDef.isRequired(propDefOpt.getBoolean(GedeonProperties.PROP_IS_REQUIRED));
			propDef.setSetability(Setability.fromString(propDefOpt.getString(GedeonProperties.PROP_SETABILITY)));
			propDef.setDisplayName(propDefOpt.getString(GedeonProperties.PROP_DISPLAY_NAME));
			propDef.setDefaultValue(propDefOpt.getString(GedeonProperties.PROP_DEFAULT_VALUE));
			//Put generated Object Class Id of PropertyDefinition class
			classDef.setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS, 
					idObjects.get(GedeonProperties.CLASS_PROPERTYDEFINITION));
			
			//Add properties definitions to class definition
			classDef.getPropertiesDefinitions().add(propDef);
		}
	}
	
	protected void createSystemClassDefinitions(Map<String, GedId> idObjects,JSONArray listClassDef) {
		Map<String,ClassDefinition> classDefinitions = new HashMap<>();
		JSONObject classDefOpt;
		for(int i = 0; i < listClassDef.length(); i++) {
			classDefOpt = listClassDef.getJSONObject(i);
			ClassDefinition classDef = factory.createClassDefinition();
			classDef.setName(classDefOpt.getString(GedeonProperties.PROP_NAME));
			LOG.info("handle ClassDefinition : {}",classDef.getName());
			classDef.isAbstract(classDefOpt.getBoolean(GedeonProperties.PROP_IS_ABSTRACT));
			classDef.isFinal(classDefOpt.getBoolean(GedeonProperties.PROP_IS_FINAL));
			//Associate parentClass
			String parentClassName = classDefOpt.getString(GedeonProperties.PROP_PARENT_CLASS_ID);
			if(StringUtils.isNotBlank(parentClassName)) {
				classDef.setParentClassDefinition(classDefinitions.get(parentClassName));
			}
			//Put generated Object Class Id of ClassDefinition class
			classDef.setPropertyValue(GedeonProperties.PROP_OBJECT_CLASS, 
					idObjects.get(GedeonProperties.CLASS_CLASSDEFINITION));
			//Set Id if pre-generated
			if(idObjects.containsKey(classDef.getName()))
				classDef.setId(idObjects.get(classDef.getName()));
			
			//Create all properties definitions associated to class
			createSystemPropertyDefinition(idObjects,classDefOpt.getJSONArray(ELT_PROPERTIES_DEFINITIONS),classDef);
			
			//Save ClassDefinition and associated propertyTemplate
			classDef.save(SaveMode.NO_REFRESH);
			classDefinitions.put(classDef.getName(),classDef);
		}
	}

	@Override
	public void deleteObject(GedeonCollection obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveObject(GedeonCollection obj, SaveMode mode) {
		super.saveObject(obj, mode);
	}
	

	

}
