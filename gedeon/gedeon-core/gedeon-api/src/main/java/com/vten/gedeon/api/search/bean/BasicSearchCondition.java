package com.vten.gedeon.api.search.bean;

import lombok.Data;

@Data
public class BasicSearchCondition {

	private boolean negate=false;
	private SearchCondition searchCondition;
	private SearchParam predicate;
	

}
