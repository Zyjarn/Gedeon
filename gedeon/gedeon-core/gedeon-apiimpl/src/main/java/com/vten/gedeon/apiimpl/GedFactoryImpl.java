package com.vten.gedeon.apiimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.ContainmentRelationship;
import com.vten.gedeon.api.GedDocument;
import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedFolder;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertiesDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.apiimpl.admin.ClassDefinitionImpl;
import com.vten.gedeon.apiimpl.property.PropertiesDefinitionImpl;
import com.vten.gedeon.apiimpl.property.PropertyDefinitionImpl;
import com.vten.gedeon.apiimpl.property.PropertyTemplateImpl;
import com.vten.gedeon.apiimpl.search.GedSearchImpl;

@Service
public class GedFactoryImpl implements GedFactory {

	@Autowired
	private AutowireCapableBeanFactory springFactory;

	protected <T> T initBean(T bean) {
		springFactory.autowireBean(bean);
		springFactory.initializeBean(bean, "bean");
		return bean;
	}

	@Override
	public GedDocument createGedDocument() {
		return initBean(new GedDocumentImpl());
	}

	@Override
	public GedDocument getGedDocument(GedId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassDefinition createClassDefinition() {
		return initBean(new ClassDefinitionImpl());

	}

	@Override
	public ClassDefinition getClassDefinition(String name) {
		ClassDefinition classDef = initBean(new ClassDefinitionImpl());
		classDef.setName(name);
		classDef.refresh();
		return classDef;
	}

	@Override
	public PropertyTemplate createPropertyTemplate() {
		return initBean(new PropertyTemplateImpl());
	}

	@Override
	public PropertyTemplate getPropertyTemplate(String name) {
		PropertyTemplate propTplt = initBean(new PropertyTemplateImpl());
		propTplt.setName(name);
		propTplt.refresh();
		return propTplt;
	}
	
	@Override
	public PropertyTemplate getPropertyTemplate(GedId id) {
		PropertyTemplate propTplt = initBean(new PropertyTemplateImpl());
		propTplt.setId(id);
		propTplt.refresh();
		return propTplt;
	}
	
	@Override
	public PropertiesDefinition createPropertiesDefinition() {
		return initBean(new PropertiesDefinitionImpl());
	}
	
	@Override
	public PropertyDefinition createPropertyDefinition() {
		return initBean(new PropertyDefinitionImpl());
	}

	@Override
	public PropertyDefinition createPropertyDefinition(String propTpltName) {
		PropertyTemplate propTplt = getPropertyTemplate(propTpltName);
		return initBean(new PropertyDefinitionImpl(propTplt));
	}

	@Override
	public PropertyDefinition getPropertyDefinition(GedId id) {
		PropertyDefinition propTplt = initBean(new PropertyDefinitionImpl());
		return propTplt;
	}
	
	@Override
	public GedFolder createGedFolder() {
		return initBean(new GedFolderImpl());
	}

	@Override
	public GedFolder getGedFolder(GedId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContainmentRelationship createContainmentRelationship() {
		return initBean(new ContainmentRelationshipImpl());
	}

	@Override
	public ContainmentRelationship getContainmentRelationship(GedId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GedSearch createEmptySearch() {
		return initBean(new GedSearchImpl());
	}

	

}
