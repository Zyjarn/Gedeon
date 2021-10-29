package com.vten.gedeon.api;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.utils.GedId;

public interface GedFactory {
	
	public GedeonCollection createGedCollection();
	
	public GedeonCollection createGedCollection(String name);
	
	public GedeonCollection getGedCollection(String name);

	public GedDocument createGedDocument(GedeonCollection collection);
	
	public GedDocument getGedDocument(GedeonCollection collection,GedId id);
	
	public ClassDefinition createClassDefinition(GedeonCollection collection);
	
	public ClassDefinition getClassDefinition(GedeonCollection collection,String name);
	
	
	/**
	 * Create a new instance of PropertyTemplate	
	 * @return empty PropertyTemplate object
	 */
	public PropertyTemplate createPropertyTemplate(GedeonCollection collection);
	
	/**
	 * Get a instance of existing PropertyTemplate by its name
	 * @return PropertyTemplate object
	 */
	public PropertyTemplate getPropertyTemplate(GedeonCollection collection,String name);
	
	/**
	 * Get a instance of existing PropertyTemplate by its id
	 * @return PropertyTemplate object
	 */
	public PropertyTemplate getPropertyTemplate(GedeonCollection collection,GedId id);

	
	/**
	 * Get an empty instance of PropertyDefinition
	 * - instance must be associated to a PropertyTemplate with 
	 * @return
	 */
	public PropertyDefinition createPropertyDefinition(GedeonCollection collection);
	
	/**
	 * Get an empty instance of PropertyDefinition associated to the 
	 * given propertyTemplate.
	 * PropertyTemplate will be fetched from server
	 * @param propTpltName a valid PropertyTemplate name
	 * @return new instance of PropertyDefinition
	 */
	public PropertyDefinition createPropertyDefinition(GedeonCollection collection,String propTpltName);
	
	public PropertyDefinition getPropertyDefinition(GedeonCollection collection,GedId id);

	public GedFolder createGedFolder(GedeonCollection collection);
	
	public GedFolder getGedFolder(GedeonCollection collection,GedId id);

	public ContainmentRelationship createContainmentRelationship(GedeonCollection collection);
	
	public ContainmentRelationship getContainmentRelationship(GedeonCollection collection,GedId id);
	
	public GedSearch createEmptySearch(GedeonCollection collection);

	

	
}
