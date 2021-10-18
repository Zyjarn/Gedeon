package com.vten.gedeon.api;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertiesDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.utils.GedId;

public interface GedFactory {
	
	public GedeonCollection createGedCollection();
	
	public GedeonCollection createGedCollection(String name);
	
	public GedeonCollection getGedCollection(String name);

	public GedDocument createGedDocument();
	
	public GedDocument getGedDocument(GedId id);
	
	public ClassDefinition createClassDefinition();
	
	public ClassDefinition getClassDefinition(String name);
	
	
	/**
	 * Create a new instance of PropertyTemplate	
	 * @return empty PropertyTemplate object
	 */
	public PropertyTemplate createPropertyTemplate();
	
	/**
	 * Get a instance of existing PropertyTemplate by its name
	 * @return PropertyTemplate object
	 */
	public PropertyTemplate getPropertyTemplate(String name);
	
	/**
	 * Get a instance of existing PropertyTemplate by its id
	 * @return PropertyTemplate object
	 */
	public PropertyTemplate getPropertyTemplate(GedId id);
	
	/**
	 * Create an empty list of property definition 
	 * @return empty PropertiesDefinition object
	 */
	public PropertiesDefinition createPropertiesDefinition();
	
	/**
	 * Get an empty instance of PropertyDefinition
	 * - instance must be associated to a PropertyTemplate with 
	 * @return
	 */
	public PropertyDefinition createPropertyDefinition();
	
	/**
	 * Get an empty instance of PropertyDefinition associated to the 
	 * given propertyTemplate.
	 * PropertyTemplate will be fetched from server
	 * @param propTpltName a valid PropertyTemplate name
	 * @return new instance of PropertyDefinition
	 */
	public PropertyDefinition createPropertyDefinition(String propTpltName);
	
	public PropertyDefinition getPropertyDefinition(GedId id);

	public GedFolder createGedFolder();
	
	public GedFolder getGedFolder(GedId id);

	public ContainmentRelationship createContainmentRelationship();
	
	public ContainmentRelationship getContainmentRelationship(GedId id);
	
	public GedSearch createEmptySearch();

	

	
}
