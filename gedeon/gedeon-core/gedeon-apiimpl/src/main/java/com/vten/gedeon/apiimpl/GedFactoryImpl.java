package com.vten.gedeon.apiimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.ContainmentRelationship;
import com.vten.gedeon.api.GedDocument;
import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedFolder;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.utils.GedEvents;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.apiimpl.admin.ClassDefinitionImpl;
import com.vten.gedeon.apiimpl.admin.PropertyDefinitionImpl;
import com.vten.gedeon.apiimpl.admin.PropertyTemplateImpl;
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
	public GedeonCollection createGedCollection() {
		GedeonCollectionImpl collection = initBean(new GedeonCollectionImpl());
		collection.getPendingEvents().add(GedEvents.CREATE);
		return collection;
	}

	@Override
	public GedeonCollection createGedCollection(String name) {
		GedeonCollectionImpl collection = initBean(new GedeonCollectionImpl());
		collection.setName(name);
		collection.getPendingEvents().add(GedEvents.CREATE);
		return collection;
	}

	@Override
	public GedeonCollection getGedCollection(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GedDocument createGedDocument(GedeonCollection collection) {
		GedDocumentImpl newDoc = initBean(new GedDocumentImpl());
		newDoc.setGedeonCollection(collection);
		newDoc.getPendingEvents().add(GedEvents.CREATE);
		return newDoc;
	}

	@Override
	public GedDocument getGedDocument(GedeonCollection collection,GedId id) {
		GedDocumentImpl newDoc = initBean(new GedDocumentImpl());
		newDoc.setGedeonCollection(collection);
		newDoc.setId(id);
		newDoc.refresh();
		return newDoc;
	}

	@Override
	public ClassDefinition createClassDefinition(GedeonCollection collection) {
		ClassDefinitionImpl classDef = initBean(new ClassDefinitionImpl());
		classDef.setGedeonCollection(collection);
		classDef.getPendingEvents().add(GedEvents.CREATE);
		return classDef;

	}

	@Override
	public ClassDefinition getClassDefinition(GedeonCollection collection,String name) {
		ClassDefinition classDef = initBean(new ClassDefinitionImpl());
		classDef.setGedeonCollection(collection);
		classDef.setName(name);
		classDef.refresh();
		return classDef;
	}

	@Override
	public PropertyTemplate createPropertyTemplate(GedeonCollection collection) {
		PropertyTemplateImpl propTplt = initBean(new PropertyTemplateImpl());
		propTplt.setGedeonCollection(collection);
		propTplt.getPendingEvents().add(GedEvents.CREATE);
		return propTplt;
	}

	@Override
	public PropertyTemplate getPropertyTemplate(GedeonCollection collection,String name) {
		PropertyTemplate propTplt = initBean(new PropertyTemplateImpl());
		propTplt.setGedeonCollection(collection);
		propTplt.setName(name);
		propTplt.refresh();
		return propTplt;
	}
	
	@Override
	public PropertyTemplate getPropertyTemplate(GedeonCollection collection,GedId id) {
		PropertyTemplate propTplt = initBean(new PropertyTemplateImpl());
		propTplt.setGedeonCollection(collection);
		propTplt.setId(id);
		propTplt.refresh();
		return propTplt;
	}
	
	@Override
	public PropertyDefinition createPropertyDefinition(GedeonCollection collection) {
		PropertyDefinition propDef = initBean(new PropertyDefinitionImpl());
		propDef.setGedeonCollection(collection);
		return propDef;
	}

	@Override
	public PropertyDefinition createPropertyDefinition(GedeonCollection collection,String propTpltName) {
		PropertyTemplate propTplt = getPropertyTemplate(collection,propTpltName);
		PropertyDefinition propDef = initBean(new PropertyDefinitionImpl(propTplt));
		propDef.setGedeonCollection(collection);
		return propDef;
	}

	@Override
	public PropertyDefinition getPropertyDefinition(GedeonCollection collection,GedId id) {
		PropertyDefinition propDef = initBean(new PropertyDefinitionImpl());
		propDef.setGedeonCollection(collection);
		propDef.setId(id);
		propDef.refresh();
		return propDef;
	}
	
	@Override
	public GedFolder createGedFolder(GedeonCollection collection) {
		return initBean(new GedFolderImpl());
	}

	@Override
	public GedFolder getGedFolder(GedeonCollection collection,GedId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContainmentRelationship createContainmentRelationship(GedeonCollection collection) {
		return initBean(new ContainmentRelationshipImpl());
	}

	@Override
	public ContainmentRelationship getContainmentRelationship(GedeonCollection collection,GedId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GedSearch createEmptySearch(GedeonCollection collection) {
		GedSearch search = initBean(new GedSearchImpl());
		search.setGedeonCollection(collection);
		return search;
	}
	

}
