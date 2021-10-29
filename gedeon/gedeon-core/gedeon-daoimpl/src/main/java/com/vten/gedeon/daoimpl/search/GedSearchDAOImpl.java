package com.vten.gedeon.daoimpl.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.connector.GedeonDBConnector;
import com.vten.gedeon.connector.GedeonDBSearch;
import com.vten.gedeon.dao.data.GedeonDBObject;
import com.vten.gedeon.dao.search.GedSearchDAO;
import com.vten.gedeon.daoimpl.GedeonDAO;

@Service
 class GedSearchDAOImpl extends GedeonDAO implements GedSearchDAO{
	
	private GedeonDBSearch searchUtil;
	
	@Autowired
	public GedSearchDAOImpl(GedeonDBConnector dbConnect, GedFactory factory,GedeonDBSearch dbSearchUtil) {
		super(dbConnect, factory);
		searchUtil = dbSearchUtil;
	}

	@Override
	public List<PersistableObject> search(GedSearch search) {
		List<PersistableObject> result = new ArrayList<>();
		//
		String collecName = search.getGedeonCollection() == null ? StringUtils.EMPTY : 
			search.getGedeonCollection().getName();
		
		//Get search result by format given search
		List<GedeonDBObject> searchRes = connector.search(collecName,search.getTargetObjectClassName(), 
				searchUtil.format(search));
		for(GedeonDBObject obj : searchRes) {
			result.add(fillPersistableObjectInstance(
					getInstanceByClassName(search.getGedeonCollection(),search.getTargetObjectClassName())
					,obj));
		}
		return result;
	}

	@Override
	public int searchCount(GedSearch search) {
		// TODO Auto-generated method stub
		return 0;
	}

}
