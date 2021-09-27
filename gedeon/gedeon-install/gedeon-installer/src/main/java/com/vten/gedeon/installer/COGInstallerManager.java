package com.vten.gedeon.installer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.PropertyDefinition;
import com.vten.gedeon.api.property.PropertyTemplate;
import com.vten.gedeon.apiimpl.OEFactoryImpl;
import com.vten.gedeon.utils.SaveMode;

@Component
public class COGInstallerManager {
	
	public static final String ELT_PROPERTY_TEMPLATE = "PropertyTemplate";
	public static final String ELT_CLASS_DEFINITIONS = "ClassDefinitions";
	public static final String ELT_PROPERTIES_DEFINITIONS = "PropertiesDefinitions";

	@Autowired
	OEFactoryImpl factory;
	
	public static final String ELT_PROPTPLT_NAME = "Name";
	public static final String ELT_PROPTPLT_TYPE = "Type";
	public static final String ELT_PROPTPLT_LIST = "List";
	public static final String ELT_PROPTPLT_SYSTEM = "System";
	
	
	public static final String ELT_PROPDEF_PROPTPLT = "PropertyTemplate";
	public static final String ELT_PROPDEF_DISPLAYNAME = "DisplayName";
	public static final String ELT_PROPDEF_REQUIRED = "Required";
	public static final String ELT_PROPDEF_DEFAULT = "DefaultValue";
	public static final String ELT_PROPDEF_SETABILITY = "Setability";
	
	private Map<String,Object> mapConfig;

	public void initDBFromJson(Map<String,Object> configDB) {
		mapConfig = configDB;
		if(mapConfig.containsKey(ELT_PROPERTY_TEMPLATE)) {
			List<Map<?,?>> listPropTplt =  getListJsonObject(mapConfig,ELT_PROPERTY_TEMPLATE);
			for(Map<?,?> propTpltMapObj : listPropTplt) {
				PropertyTemplate propTemplate = factory.createPropertyTemplate();
				propTemplate.setName((String)propTpltMapObj.get(ELT_PROPTPLT_NAME));
				propTemplate.setType((String)propTpltMapObj.get(ELT_PROPTPLT_TYPE));
				propTemplate.isList((Boolean)propTpltMapObj.get(ELT_PROPTPLT_LIST));
				propTemplate.setIsSystem((Boolean)propTpltMapObj.get(ELT_PROPTPLT_SYSTEM));
				propTemplate.save(SaveMode.NO_REFRESH);
			}
		} else {
			//throw something
		}
		//TODO in case of errors, remove all created objects
		
		if(mapConfig.containsKey(ELT_CLASS_DEFINITIONS)) {
			List<Map<?,?>> listClassDef = getListJsonObject(mapConfig,ELT_CLASS_DEFINITIONS);
			for(Map<?,?> classDefMapObj : listClassDef) {
				ClassDefinition classDef = factory.createClassDefinition();
				classDef.setName((String)classDefMapObj.get(ELT_PROPTPLT_NAME));
				
				List<Map<?,?>> listPropDefs = getListJsonObject(classDefMapObj,ELT_PROPERTIES_DEFINITIONS);
				for(Map<?,?> propTpltMapObj : listPropDefs) {
					PropertyDefinition propDef = factory.createPropertyDefinition((String)propTpltMapObj.get(ELT_PROPDEF_PROPTPLT));
					propDef.setName((String)propTpltMapObj.get(ELT_PROPTPLT_NAME));
					classDef.getPropertiesDefinitions().add(propDef);
				}
				;
				classDef.save(SaveMode.NO_REFRESH);
			}
				
		}
		
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
