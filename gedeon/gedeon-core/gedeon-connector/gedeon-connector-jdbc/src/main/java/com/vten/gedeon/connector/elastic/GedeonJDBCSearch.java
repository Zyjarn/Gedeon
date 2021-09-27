package com.vten.gedeon.connector.elastic;

import org.apache.commons.lang3.StringUtils;

import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.search.bean.BasicSearchCondition;
import com.vten.gedeon.api.search.bean.SearchCondition;
import com.vten.gedeon.api.search.bean.SearchParam;

public class GedeonJDBCSearch  {

	
	public void search() {
		
	}
	
	public String format(GedSearch search) {
		return toSQL(search);
	}
	
	protected String toSQL(GedSearch search) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		//TOP 
		.append(String.join(",",search.getSelectColumn()))
		.append(" FROM ")
		.append(search.getTargetObjectClassName())
		.append(" WHERE ")
		.append(toSQL(search.getSearchCondition()));
		return sb.toString();
	}
	
	protected String toSQL(SearchCondition searchCondition) {
		StringBuilder sb = new StringBuilder();
		sb.append(toSQL(searchCondition.getBasicCondition()));
		if(searchCondition.getOperator() != null && searchCondition != null) {
			sb.append(StringUtils.SPACE).append(searchCondition.getOperator().toString())
				.append(StringUtils.SPACE).append(toSQL(searchCondition));
		}
			
		return sb.toString();
	}
	
	protected String toSQL(BasicSearchCondition basicSC) {
		if(basicSC.getPredicate() == null && basicSC.getSearchCondition() == null) {
			return "1=1";
		}
		return !basicSC.isNegate() ? toSQL(basicSC.getPredicate()) : 
			new StringBuilder().append("NOT (")
				.append(toSQL(basicSC.getSearchCondition()))
				.append(")").toString();
				
	}
	
	protected String toSQL(SearchParam param) {
		StringBuilder sb = new StringBuilder(param.getColumnName());
		switch(param.getOperator()) {
		case BETWEEN:sb.append(" BETWEEN ").append(valueToSQL(param.getValues()[0]))
			.append(" TO ").append(valueToSQL(param.getValues()[1]));
			break;
		case EQUALS:sb.append(" = ").append(valueToSQL(param.getValue()));
			break;
		case GREATERTHAN:sb.append(" > ").append(valueToSQL(param.getValue()));
			break;
		case GREATERTHANOREQUALS:sb.append(" >= ").append(valueToSQL(param.getValue()));
			break;
		case IN:sb.append(" >= ").append(valueToSQL(param.getValue()));
			break;
		case ISNOTNULL:sb.append(" is not null");
			break;
		case ISNULL:sb.append(" is null");
			break;
		case LIKE:sb.append(" like '%").append(param.getValue().toString()).append("%'");
			break;
		case LOWERTHAN:sb.append(" < ").append(valueToSQL(param.getValue()));
			break;
		case LOWERTHANOREQUALS:sb.append(" <= ").append(valueToSQL(param.getValue()));
			break;
		default:
			break;
		
		}
		return sb.toString();
	}
	
	protected String valueToSQL(Object value) {
		if(value instanceof String) {
			return "'".concat(value.toString()).concat("'");
		} else
			return value.toString();
	}
}
