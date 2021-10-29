package com.vten.gedeon.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.admin.PropertyTemplate;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.api.utils.GedeonProperties;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.dao.PropertyTemplateDAO;
import com.vten.gedeon.utils.SaveMode;

@Service
public class PropertyTemplateDAOImpl extends GedeonDAO implements PropertyTemplateDAO {

	@Autowired
	public PropertyTemplateDAOImpl(GedeonDBConnector dbConnect, GedFactory factory) {
		super(dbConnect, factory);
	}

	@Cacheable(cacheNames="PropertyTemplateId", key="#id.value")
	@Override
	public PropertyTemplate getObject(GedeonCollection collection,GedId id) {
		return (PropertyTemplate) super.getObjectById(factory.createPropertyTemplate(collection),
				GedeonProperties.CLASS_PROPERTYTEMPLATE, id.getValue());
	}

	@Cacheable(cacheNames="PropertyTemplateName", key="#name")
	@Override
	public PropertyTemplate getObject(GedeonCollection collection,String name) {
		return (PropertyTemplate) super.getObjectByName(collection,GedeonProperties.CLASS_PROPERTYTEMPLATE, name);
	}

	@Caching(evict = { 
			  @CacheEvict(cacheNames="PropertyTemplateId", key="#obj.getId"), 
			  @CacheEvict(cacheNames="PropertyTemplateName", key="#obj.getName") })
	@Override
	public void saveObject(PropertyTemplate obj, SaveMode mode) {
		if(!obj.isSystem()) {
			//TODO checkproperties
		}
		super.saveObject(obj, mode);
	}

	@Override
	public void deleteObject(PropertyTemplate obj) {
		// TODO Auto-generated method stub
		
	}


}
