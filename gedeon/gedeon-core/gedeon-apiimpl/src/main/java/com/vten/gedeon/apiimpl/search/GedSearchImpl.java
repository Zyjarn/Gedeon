package com.vten.gedeon.apiimpl.search;

import java.util.List;

import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.search.bean.SearchCondition;

import lombok.Data;

@Data
public class GedSearchImpl implements GedSearch{
	
	private List<String> selectColumn;
	private String targetObjectClassName;
	
	private SearchCondition searchCondition;

	private boolean isCount;	

}
