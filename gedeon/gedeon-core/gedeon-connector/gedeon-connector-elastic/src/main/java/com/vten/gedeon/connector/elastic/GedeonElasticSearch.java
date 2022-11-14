package com.vten.gedeon.connector.elastic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.api.search.SearchOperator;
import com.vten.gedeon.api.search.bean.SearchCondition;
import com.vten.gedeon.api.search.bean.SearchParam;
import com.vten.gedeon.connector.GedeonDBSearch;

@Service
public class GedeonElasticSearch implements GedeonDBSearch {
	/**
	 * 
	 * { "query": { "prefix" : { "user" : "ki" } } }
	 * 
	 * 
	 * 
	 * "query":{ "function_score":{ "query":{ "bool":{ "should": [ { "range": {
	 * "allBoost": { "gt": 0 } } }
	 * 
	 * 
	 * 
	 * { "query": { "bool": { "must_not": [ { "exists": { "field": "company" } } ] }
	 * } }
	 */

	@Override
	public String format(GedSearch search) {

		return toLucene(search);
	}

	protected String toLucene(GedSearch search) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"seq_no_primary_term\": true,\"query\": ");

		sb.append(toLucene(search.getSearchCondition()));

		// TODO handle all
		if (!search.getSelectColumn().contains("*")) {
			sb.append(", \"fields\":[");
			sb.append(formatValues(search.getSelectColumn().toArray()));
			sb.append("],\"_source\": false");
		}
		// TODO format date ?
		sb.append("}");

		// .append(search.getTargetObjectClassName())

		return sb.toString();
	}

	protected String toLucene(SearchCondition searchCondition) {
		StringBuilder sb = new StringBuilder("{");
		if (searchCondition.getBasicCondition().isNegate()) {
			sb.append(" \"bool\": {\"must_not\":[");
		} else if ((searchCondition.getOperator() == null)
				|| SearchOperator.AND.equals(searchCondition.getOperator())) {
			sb.append(" \"bool\": {\"must\":[");
		} else {
			sb.append(" \"bool\": {\"should\":[");
		}
		if (searchCondition.getBasicCondition().getPredicate() != null) {
			sb.append(toLucene(searchCondition.getBasicCondition().getPredicate()));
		} else {
			sb.append(toLucene(searchCondition.getBasicCondition().getSearchCondition()));
		}
		if (searchCondition.getSearchCondition() != null) {
			sb.append(",").append(toLucene(searchCondition.getSearchCondition()));
		}
		sb.append("]}}");

		return sb.toString();
	}

	protected String toLucene(SearchParam param) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		switch (param.getOperator()) {
		case BETWEEN:
			sb.append("\"range\":{\"").append(param.getColumnName()).append("\":{\"gte\":")
					.append(formatValue(param.getValues()[0])).append(",\"lte\":")
					.append(formatValue(param.getValues()[1])).append("}}");
			break;
		case EQUALS:
			sb.append("\"match\":{\"").append(param.getColumnName()).append("\":\"").append(param.getValue().toString())
					.append("\"}");
			break;
		case GREATERTHAN:
			sb.append("\"range\":{\"").append(param.getColumnName()).append("\":{\"gt\":")
					.append(formatValue(param.getValue())).append("}}");
			break;
		case GREATERTHANOREQUALS:
			sb.append("\"range\":{\"").append(param.getColumnName()).append("\":{\"gte\":")
					.append(formatValue(param.getValue())).append("}}");
			break;
		case IN:
			sb.append("\"exists\":{\"field\":\"").append(param.getColumnName()).append("\"}");
			break;
		case ISNOTNULL:
			sb.append(" is not null");
			break;
		case ISNULL:
			// TODO to correct, especially when handled in a "not" clause
			sb.append("\"bool\":{\"must_not\":[{\"exists\":{\"field\":\"").append(param.getColumnName())
					.append("\"}}]}");
			break;
		case LIKE:
			sb.append("\"match\":{\"").append(param.getColumnName()).append("\":\"").append(param.getValue().toString())
					.append("\"}");
			break;
		case LOWERTHAN:
			sb.append("\"range\":{\"").append(param.getColumnName()).append("\":{\"lt\":")
					.append(formatValue(param.getValue())).append("}}");
			break;
		case LOWERTHANOREQUALS:
			sb.append("\"range\":{\"").append(param.getColumnName()).append("\":{\"lte\":")
					.append(formatValue(param.getValue())).append("}}");
			break;
		default:
			break;

		}
		sb.append("}");
		return sb.toString();
	}

	protected String formatValue(Object value) {
		if (value == null) {
			return "null";
		}
		if (value instanceof String) {
			return "\"".concat(value.toString()).concat("\"");
		} else {
			return value.toString();
		}
	}

	protected String formatValues(Object[] value) {
		if ((value == null) || (value.length == 0)) {
			return StringUtils.EMPTY;
		}
		String[] values = new String[value.length];
		for (int i = 0; i < value.length; i++) {
			values[i] = formatValue(value[i]);
		}
		return StringUtils.join(values, ",");
	}
}
