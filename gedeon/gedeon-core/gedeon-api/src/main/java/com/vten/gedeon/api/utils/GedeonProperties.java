package com.vten.gedeon.api.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class GedeonProperties {

	public static final String PROP_ID = "Id";
	public static final String PROP_CLASSNAME = "ClassName";
	public static final String PROP_NAME = "Name";
	public static final String PROP_CONTAINER = "Container";
	public static final String PROP_CONTAINABLE = "Containable";
	public static final String PROP_CONTAINERS = "Containers";
	public static final String PROP_CONTAINEES = "Containees";
	public static final String PROP_DATE_ADDED_ON = "AddedOn";
	public static final String PROP_ADDED_BY = "AddedBy";
	public static final String PROP_DATE_SAVED = "DateSaved";
	public static final String PROP_LAST_MODIFIER = "LastModifier";

	// PROPERTY TEMPLATE / DEFINITION
	public static final String PROP_PROPERTY_TYPE = "PropertyType";
	public static final String PROP_IS_LIST = "IsList";
	public static final String PROP_IS_SYSTEM = "IsSystem";

	public static final String PROP_IS_REQUIRED = "IsRequired";
	public static final String PROP_DISPLAY_NAME = "DisplayName";
	public static final String PROP_DEFAULT_VALUE = "DefaultValue";
	public static final String PROP_SETABILITY = "Setability";
	public static final String PROP_PROPERTY_TEMPLATE_ID = "PropertyTemplateId";
	public static final String PROP_ASSOCIATED_CLASS_ID = "AssociatedClassId";

	// CLASS DEFINITION
	public static final String PROP_IS_ABSTRACT = "IsAbstract";
	public static final String PROP_IS_FINAL = "IsFinal";
	public static final String PROP_PARENT_CLASS_ID = "ParentClassId";
	public static final String PROP_PROPERTIES_DEFINITION = "PropertiesDefinitions";

	// PROPERTY
	public static final String PROP_PROPERTY_DEFINITION = "PropertyDefinition";

	// AbstractPersistable
	public static final String PROP_OBJECT_CLASS = "ObjectClassId";

	// COGDOCUMENT
	public static final String PROP_MAJOR_VERSION = "MajorVersion";
	public static final String PROP_MINOR_VERSION = "MinorVersion";
	public static final String PROP_IS_RESERVED = "IsReserved";
	public static final String PROP_IS_CURRENT_VERSION = "IsCurrentVersion";
	public static final String PROP_VERSIONS_IDS = "VersionsIds";
	// STORABLE
	public static final String PROP_STORAGE = "Storage";
	public static final String PROP_CONTENT_NAME = "ContentName";
	public static final String PROP_STORED_CONTENT = "StoredContent";
	public static final String PROP_STORED_CONTENT_SIZE = "StoredContentSize";
	public static final String PROP_MIME_TYPE = "MimeType";

	// STORAGE
	public static final String PROP_STORAGE_TYPE = "StorageType";
	public static final String PROP_STORAGE_HOST = "StorageHost";
	public static final String PROP_STORAGE_USERNAME = "StorageUsername";
	public static final String PROP_STORAGE_CREDENTIAL = "StorageCredential";
	public static final String PROP_STORAGE_IS_ENCRYPTED = "IsStorageEncrypted";
	public static final String PROP_STORAGE_BASENAME = "StorageBasename";
	public static final String PROP_STORAGE_IS_DEFAULT = "IsStorageDefault";

	/*
	 * CLASS
	 */
	public static final String CLASS_ABSTRACTDOCUMENT = "AbstractDocument";
	public static final String CLASS_ABSTRACTPERSISTABLEOBJECT = "AbstractPersistableObject";
	public static final String CLASS_GEDCOLLECTION = "GedeonCollection";
	public static final String CLASS_CLASSDEFINITION = "ClassDefinition";
	public static final String CLASS_GEDDOCUMENT = "GedDocument";
	public static final String CLASS_PROPERTYDEFINITION = "PropertyDefinition";
	public static final String CLASS_PROPERTYTEMPLATE = "PropertyTemplate";
	public static final String CLASS_CONTAINMENTRELATIONSHIP = "ContainmentRelationship";
	public static final String CLASS_GEDFOLDER = "GedFolder";
	public static final String CLASS_STORAGE = "Storage";

	public static final List<String> ALL_PROPERTIES = getAllProperties();

	private static List<String> getAllProperties() {
		List<String> list = new ArrayList<>();
		list.add(PROP_ID);
		list.add(PROP_CLASSNAME);
		list.add(PROP_NAME);
		list.add(PROP_CONTAINER);
		list.add(PROP_CONTAINABLE);
		list.add(PROP_CONTAINERS);
		list.add(PROP_CONTAINEES);
		list.add(PROP_DATE_ADDED_ON);
		list.add(PROP_ADDED_BY);
		list.add(PROP_DATE_SAVED);
		list.add(PROP_LAST_MODIFIER);
		list.add(PROP_PROPERTY_TYPE);
		list.add(PROP_IS_LIST);
		list.add(PROP_IS_REQUIRED);
		list.add(PROP_PROPERTIES_DEFINITION);
		list.add(PROP_PROPERTY_DEFINITION);
		list.add(PROP_MAJOR_VERSION);
		list.add(PROP_MINOR_VERSION);
		list.add(PROP_IS_RESERVED);
		list.add(PROP_IS_CURRENT_VERSION);
		list.add(PROP_VERSIONS_IDS);
		list.add(PROP_STORAGE);
		list.add(PROP_CONTENT_NAME);
		list.add(PROP_STORED_CONTENT);
		list.add(PROP_STORED_CONTENT_SIZE);
		list.add(PROP_MIME_TYPE);
		return list;
	}
}
