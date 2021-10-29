package com.vten.gedeon.installer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.apiimpl.GedFactoryImpl;
import com.vten.gedeon.utils.SaveMode;

@Component
public class GedeonInstallerManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(GedeonInstallerManager.class);
	
	public static final String ELT_PROPERTY_TEMPLATE = "PropertyTemplate";
	public static final String ELT_CLASS_DEFINITIONS = "ClassDefinitions";
	public static final String ELT_PROPERTIES_DEFINITIONS = "PropertiesDefinitions";

	@Autowired
	GedFactoryImpl factory;
	
	private Map<String,Object> mapConfig;

	public void initDBFromJson(Map<String,Object> configDB) {
		
		GedeonCollection col = factory.createGedCollection("SII");
		col.save(SaveMode.REFRESH);
		
//		//Init Id for 
//		GedId propTpltClassId = GedId.newIdFromValue(GedeonProperties.CLASS_PROPERTYTEMPLATE.getBytes());
//		
//		mapConfig = configDB;
//		if(mapConfig.containsKey(ELT_PROPERTY_TEMPLATE)) {
//			List<Map<?,?>> listPropTplt =  getListJsonObject(mapConfig,ELT_PROPERTY_TEMPLATE);
//			for(Map<?,?> propTpltMapObj : listPropTplt) {
//				PropertyTemplate propTemplate = factory.createPropertyTemplate();
//				propTemplate.setName((String)propTpltMapObj.get(GedeonProperties.PROP_NAME));
//				LOG.info("handle PropertyTemplate : {}",propTemplate.getName());
//				propTemplate.setType((String)propTpltMapObj.get(GedeonProperties.PROP_PROPERTY_TYPE));
//				propTemplate.isList((Boolean)propTpltMapObj.get(GedeonProperties.PROP_IS_LIST));
//				propTemplate.setIsSystem((Boolean)propTpltMapObj.get(GedeonProperties.PROP_IS_SYSTEM));
//				propTemplate.save(SaveMode.NO_REFRESH);
//			}
//		} else {
//			//throw something
//		}
//		//TODO in case of errors, remove all created objects
//		
//		Map<String,ClassDefinition> classDefinitions = new HashMap<>();
//		
//		if(mapConfig.containsKey(ELT_CLASS_DEFINITIONS)) {
//			List<Map<?,?>> listClassDef = getListJsonObject(mapConfig,ELT_CLASS_DEFINITIONS);
//			for(Map<?,?> classDefMapObj : listClassDef) {
//				ClassDefinition classDef = factory.createClassDefinition();
//				classDef.setName((String)classDefMapObj.get(GedeonProperties.PROP_NAME));
//				LOG.info("handle ClassDefinition : {}",classDef.getName());
//				classDef.isAbstract((boolean) classDefMapObj.get(GedeonProperties.PROP_IS_ABSTRACT));
//				classDef.isFinal((boolean) classDefMapObj.get(GedeonProperties.PROP_IS_FINAL));
//				//Associate parentClass
//				String parentClassName = (String)classDefMapObj.get(GedeonProperties.PROP_PARENT_CLASS_ID);
//				if(StringUtils.isNotBlank(parentClassName)) {
//					classDef.setParentClassDefinition(classDefinitions.get(parentClassName));
//				}
//				
//				//Create all properties definitions associated to class
//				List<Map<?,?>> listPropDefs = getListJsonObject(classDefMapObj,ELT_PROPERTIES_DEFINITIONS);
//				for(Map<?,?> propTpltMapObj : listPropDefs) {
//					//Create property definition with name of associated entry template
//					String propertyTemplate = (String)propTpltMapObj.get(GedeonProperties.CLASS_PROPERTYTEMPLATE);
//					PropertyDefinition propDef = factory.createPropertyDefinition(propertyTemplate);
//					propDef.setName(propertyTemplate);
//					LOG.info("handle PropertyDefinition : {}",propDef.getName());
//					propDef.isRequired((boolean) propTpltMapObj.get(GedeonProperties.PROP_IS_REQUIRED));
//					propDef.setSetability(Setability.fromString((String)propTpltMapObj.get(GedeonProperties.PROP_SETABILITY)));
//					propDef.setDisplayName((String)propTpltMapObj.get(GedeonProperties.PROP_DISPLAY_NAME));
//					propDef.setDefaultValue(propTpltMapObj.get(GedeonProperties.PROP_DEFAULT_VALUE));
//					//Add properties definitions to class definition
//					classDef.getPropertiesDefinitions().add(propDef);
//				}
//				//Save ClassDefinition and associated propertyTemplate
//				classDef.save(SaveMode.NO_REFRESH);
//				classDefinitions.put(classDef.getName(),classDef);
//			}
//				
//		}
		
	}
	
	private List<Map<?,?>> getListJsonObject(Map<?,?> jsonObj, String eltName){
		List<Map<?,?>> list = new ArrayList<>();
		Object classDefsObj = jsonObj.get(eltName);
		if(classDefsObj instanceof List) {
			List<?> listClassDefsObj = (List<?>)classDefsObj;
			for(Object classDefObj : listClassDefsObj) {
				if(classDefObj instanceof Map) {
					list.add((Map<?,?>) classDefObj);
				}
			}
		}
		return list;
	}
}
