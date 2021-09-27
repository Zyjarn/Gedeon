package com.vten.gedeon.api;

import com.vten.gedeon.api.admin.ClassDefinition;
import com.vten.gedeon.api.property.PropertyDefinition;
import com.vten.gedeon.api.property.PropertyTemplate;

public interface GedFactory {

	public GedDocument createOEDocument();
	
	public ClassDefinition createClassDefinition();
	
	//PropertyTemplate
	
	/**
	 * Create a new instance of PropertyTemplate	
	 * @return empty PropertyTemplate object
	 */
	public PropertyTemplate createPropertyTemplate();
	/**
	 * Get a instance of existing PropertyTemplate by his name
	 * @return PropertyTemplate object
	 */
	public PropertyTemplate getPropertyTemplate(String name);
	//PropertyDefinition
	
	public PropertyDefinition createPropertyDefinition(String propTpltName);

	public GedFolder createOEFolder();

	public ContainmentRelationship createContainmentRelationship();
}
