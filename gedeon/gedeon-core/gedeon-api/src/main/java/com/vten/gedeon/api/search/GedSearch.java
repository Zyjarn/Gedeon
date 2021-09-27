package com.vten.gedeon.api.search;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vten.gedeon.api.factory.OEFactory;
import com.vten.gedeon.api.search.bean.SearchCondition;
import com.vten.gedeon.api.search.bean.SearchParam;
import com.vten.gedeon.exception.OERuntimeException;

public interface GedSearch {
	
	public List<String> getSelectColumn();
	
	public void setSelectColumn(List<String> columns);
	
	public String getTargetObjectClassName();
	
	public void setTargetObjectClassName(String className);
	
	public SearchCondition getSearchCondition();
	
	public void setSearchCondition(SearchCondition searchCondition);
	
	public boolean isCount();
	
	public void setCount(boolean value);
	
	public static class OESearchBuilder {
		
		private boolean count = false;
		
		String[] columns;
		String from;
		
		private boolean selectOrCountPerformed = false;
		private boolean whereInProgress = false;
		
		public SearchCondition searchCondition = new SearchCondition();
		public SearchCondition workingCondition = searchCondition;
		public SearchCondition workingNot = null;
		public SearchCondition prevWorkingNot = null;
		
		//public boolean not = false;
		
		public GedSearch build(/*GedeonCollectionImpl*/) {
			GedSearch search = OEFactory.createEmptySearch(/*GedeonCollectionImpl*/);
			search.setCount(count);
			//SELECT
			if(columns == null || columns.length == 0)
				throw new OERuntimeException("");
			search.setSelectColumn(Arrays.asList(columns));
			//FROM
			if(StringUtils.isBlank(from))
				throw new OERuntimeException("");
			
			search.setTargetObjectClassName(from);
			//JOIN
			
			//WHERE
			if(searchCondition.getParent() != null)
				throw new OERuntimeException("Missing closing operator 'and','or' or 'not'.");
			search.setSearchCondition(searchCondition);
			
			return search;
		}
		
		public OESearchBuilder count(String columnName) {
			if(whereInProgress)
				throw new OERuntimeException("");
			count = true;
			columns = new String[] {columnName};
			selectOrCountPerformed = true;
			return this;
		}
		
		
		public OESearchBuilder select(String... columnsNames) {
			if(selectOrCountPerformed || whereInProgress)
				throw new OERuntimeException("");
			columns = columnsNames;
			selectOrCountPerformed = true;
			return this;
		}
		
		public OESearchBuilder from(String className) {
			if(whereInProgress || !selectOrCountPerformed)
				throw new OERuntimeException("");
			from = className;
			return this;
		}
		
		/**
		 * Start build of search condition
		 * @return the current search builder
		 */
		public OESearchBuilder where() {
			if(!selectOrCountPerformed)
				throw new OERuntimeException("");
			whereInProgress = true;
			return this;
		}
		
		
		public OESearchBuilder or() {
			if(!selectOrCountPerformed)
				throw new OERuntimeException("");
			workingCondition.setOperator(SearchOperator.OR);
			workingCondition.setSearchCondition(new SearchCondition(workingCondition));
			workingCondition = workingCondition.getSearchCondition();
			return this;
		}
		
		public OESearchBuilder and() {
			if(!selectOrCountPerformed)
				throw new OERuntimeException("");
			workingCondition.setOperator(SearchOperator.AND);
			workingCondition.setSearchCondition(new SearchCondition(workingCondition));
			workingCondition = workingCondition.getSearchCondition();
			return this;
		}

		public OESearchBuilder not() {
			
			workingCondition.getBasicCondition().setNegate(true);
			workingCondition.getBasicCondition().setSearchCondition(new SearchCondition(workingCondition));
			
			workingNot = workingCondition;
			workingCondition = workingCondition.getBasicCondition().getSearchCondition();
			return this;
		}
		
		public OESearchBuilder endNot() {
			if(workingNot == null) 
				throw new OERuntimeException("Can't add 'endNot' param to query without call to 'not' first.");
			workingCondition = workingNot;
			workingNot = null;
			return this;
		}
		
		public OESearchBuilder equals(String columnName, Object value) {
			
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.EQUALS,value));
			return this;
		}
		
		public OESearchBuilder greaterThan(String columnName, Object value) {
			
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.GREATERTHAN,value));
			return this;
		}
		
		public OESearchBuilder greaterThanOrEquals(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.GREATERTHANOREQUALS,value));
			return this;
		}
		
		public OESearchBuilder lowerThan(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.LOWERTHAN,value));
			return this;
		}
		
		public OESearchBuilder lowerThanOrEquals(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.LOWERTHANOREQUALS,value));
			return this;
		}
		
		public OESearchBuilder like(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.LIKE,value));
			return this;
		}
		
		public OESearchBuilder between(String columnName, Object minValue, Object maxValue) {
			
			return this;
		}
		
		public OESearchBuilder in(String columnName,Object... values ) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.IN,values));
			return this;
		}
		
		public OESearchBuilder isNull(String columnName) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.ISNULL));
			return this;
		}
		
		public OESearchBuilder isNotNull(String columnName) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.ISNOTNULL));
			return this;
		}
	}
}
