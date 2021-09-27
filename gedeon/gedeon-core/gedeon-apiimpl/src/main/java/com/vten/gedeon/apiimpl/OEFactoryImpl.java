package com.vten.gedeon.apiimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.vten.gedeon.api.ContainmentRelationship;
import com.vten.gedeon.api.OEDocument;
import com.vten.gedeon.api.OEFactory;
import com.vten.gedeon.api.OEFolder;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.PropertyDefinition;
import com.vten.gedeon.api.property.PropertyTemplate;
import com.vten.gedeon.apiimpl.property.PropertyDefinitionImpl;
import com.vten.gedeon.apiimpl.property.PropertyTemplateImpl;

@Component
public class OEFactoryImpl implements OEFactory {
	
	@Autowired
	private AutowireCapableBeanFactory springFactory;
	
	protected <T> T initBean(T bean) {
		springFactory.autowireBean( bean );
		springFactory.initializeBean( bean, "bean" );
		return bean;
	}
	
	public OEDocument createOEDocument(){
		return initBean(new OEDocumentImpl());
	}
	
	public ClassDefinition createClassDefinition(){
		return initBean(new ClassDefinitionImpl());
		
	}
	
	//PropertyTemplate
	
	/**
	 * Create a new instance of PropertyTemplate	
	 * @return empty PropertyTemplate object
	 */
	public PropertyTemplate createPropertyTemplate(){
		return initBean(new PropertyTemplateImpl());
	}
	
	/**
	 * Get a instance of existing PropertyTemplate by his name
	 * @return PropertyTemplate object
	 */
	public PropertyTemplate getPropertyTemplate(String name){
		PropertyTemplate prop = initBean(new PropertyTemplateImpl());
		prop.setName(name);
		prop.refresh();
		return prop;
	}
	
	//PropertyDefinition
	
	public PropertyDefinition createPropertyDefinition(String propTpltName){
		PropertyTemplate propTplt = getPropertyTemplate(propTpltName);
		return initBean(new PropertyDefinitionImpl(propTplt));
	}

	public OEFolder createOEFolder() {
		return initBean(new OEFolderImpl());
	}

	public ContainmentRelationship createContainmentRelationship() {
		return initBean(new ContainmentRelationshipImpl());
	}
}
