package com.vten.gedeon.api;

import java.io.InputStream;
import java.util.List;

import com.vten.gedeon.api.admin.GedeonClassDefinition;
import com.vten.gedeon.api.admin.PropertyDefinition;
import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.exception.GedeonIOException;
import com.vten.gedeon.utils.SaveMode;

public interface GedeonCollection extends PersistableObject {

	public GedFactory getFactory();

	public boolean isInit();

	public void saveObject(PersistableObject object, SaveMode mode);

	public PersistableObject refreshObject(PersistableObject object);

	public PersistableObject getObject(GedId id, String tableName);

	public PersistableObject getObject(String name, String tableName);

	public InputStream getContent(Storable storableObject) throws GedeonIOException;

	PropertyDefinition newCopyInstance(PropertyDefinition propertyDefinition);

	List<PropertyDefinition> getPropertiesDefinitionForClass(GedeonClassDefinition classDef);

	GedeonClassDefinition getClassDefinition(String name);

	GedeonClassDefinition getClassDefinition(GedId id);

	PropertyDefinition getPropertyDefinition(GedId id);

	public void saveStorage(Storage object, SaveMode mode);
}
