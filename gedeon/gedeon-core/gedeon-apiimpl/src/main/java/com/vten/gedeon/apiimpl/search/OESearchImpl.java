package com.vten.gedeon.apiimpl.search;

import java.util.List;

import com.vten.gedeon.api.search.OESearch;
import com.vten.gedeon.api.search.bean.SearchCondition;

import lombok.Data;

@Data
public class OESearchImpl implements OESearch{
	
	private List<String> selectColumn;
	private String targetObjectClassName;
	
	private SearchCondition searchCondition;

	private boolean isCount;	

}
