package com.vten.gedeon.apiimpl.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.search.bean.SearchCondition;
import com.vten.gedeon.dao.search.GedSearchDAO;

import lombok.Data;

@Data
public class GedSearchImpl implements GedSearch{
	
	@Autowired
	protected GedSearchDAO dao;
	
	private List<String> selectColumn;
	private String targetObjectClassName;
	
	private SearchCondition searchCondition;
	
	private GedeonCollection gedeonCollection;

	private boolean isCount;

	@Override
	public List<PersistableObject> search() {
		return dao.search(this);
	}	

}
