package com.vten.gedeon.api.search.bean;

import com.vten.gedeon.api.search.PredicateOperator;

import lombok.Data;

/**
 * <comparison_predicate>
                 | <null_test>
                 | <in_test>
                 | <existence_test>
                 | <isclass_test>
                 | <isOfclass_test>
                 | <content_test>
                 | <satisfies_test>
                 | <intersects_test>
 
 */
@Data
public class SearchParam {
	
	private String columnName;
	
	private PredicateOperator operator;
	
	private Object value;
	private Object[] values;
	
	public SearchParam() {
		//Nothing to do
	}
	
	public SearchParam(String columnName,PredicateOperator operator,Object value) {
		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
	}
	
	public SearchParam(String columnName,PredicateOperator operator,Object... values) {
		this.columnName = columnName;
		this.operator = operator;
		this.values = values;
	}
	
	public void setValues(Object... args) {
		values = args;
	}
	
}
