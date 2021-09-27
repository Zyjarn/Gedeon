package com.vten.gedeon.api.search.bean;

import com.vten.gedeon.api.search.SearchOperator;

import lombok.Data;

@Data
public class SearchCondition {

	private BasicSearchCondition basicCondition = new BasicSearchCondition();
	private SearchOperator operator;
	private SearchCondition searchCondition;
	
	private SearchCondition parent;
	
	public SearchCondition() {
		this.parent=null;
	}
	
	public SearchCondition(SearchCondition parent) {
		this.parent=parent;
	}

	
}
