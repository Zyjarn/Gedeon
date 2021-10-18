package com.vten.gedeon.api.search;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.search.bean.SearchCondition;
import com.vten.gedeon.api.search.bean.SearchParam;
import com.vten.gedeon.exception.GedeonRuntimeException;

public interface GedSearch {
	
	public List<PersistableObject> search();
	
	public List<String> getSelectColumn();
	
	public void setSelectColumn(List<String> columns);
	
	public String getTargetObjectClassName();
	
	public void setTargetObjectClassName(String className);
	
	public SearchCondition getSearchCondition();
	
	public void setSearchCondition(SearchCondition searchCondition);
	
	public boolean isCount();
	
	public void setCount(boolean value);
	
	public static class SearchBuilder {
		
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
		
		public GedSearch build(GedFactory factory/*GedeonCollectionImpl*/) {
			GedSearch search = factory.createEmptySearch(/*GedeonCollectionImpl*/);
			search.setCount(count);
			//SELECT
			if(columns == null || columns.length == 0)
				throw new GedeonRuntimeException("Wrong select clause.");
			search.setSelectColumn(Arrays.asList(columns));
			//FROM
			if(StringUtils.isBlank(from))
				throw new GedeonRuntimeException("");
			
			search.setTargetObjectClassName(from);
			//JOIN
			
			//WHERE
			if(searchCondition.getParent() != null)
				throw new GedeonRuntimeException("Missing closing operator 'and','or' or 'not'.");
			search.setSearchCondition(searchCondition);
			
			return search;
		}
		
		public SearchBuilder count(String columnName) {
			if(whereInProgress)
				throw new GedeonRuntimeException("");
			count = true;
			columns = new String[] {columnName};
			selectOrCountPerformed = true;
			return this;
		}
		
		public SearchBuilder selectAll() {
			if(selectOrCountPerformed || whereInProgress)
				throw new GedeonRuntimeException("");
			columns = new String[] {"*"};
			selectOrCountPerformed = true;
			return this;
		}
		
		public SearchBuilder select(String... columnsNames) {
			if(selectOrCountPerformed || whereInProgress)
				throw new GedeonRuntimeException("");
			columns = columnsNames;
			selectOrCountPerformed = true;
			return this;
		}
		
		public SearchBuilder from(String className) {
			if(whereInProgress || !selectOrCountPerformed)
				throw new GedeonRuntimeException("");
			from = className;
			return this;
		}
		
		/**
		 * Start build of search condition
		 * @return the current search builder
		 */
		public SearchBuilder where() {
			if(!selectOrCountPerformed)
				throw new GedeonRuntimeException("");
			whereInProgress = true;
			return this;
		}
		
		
		public SearchBuilder or() {
			if(!selectOrCountPerformed)
				throw new GedeonRuntimeException("");
			workingCondition.setOperator(SearchOperator.OR);
			workingCondition.setSearchCondition(new SearchCondition(workingCondition));
			workingCondition = workingCondition.getSearchCondition();
			return this;
		}
		
		public SearchBuilder and() {
			if(!selectOrCountPerformed)
				throw new GedeonRuntimeException("");
			workingCondition.setOperator(SearchOperator.AND);
			workingCondition.setSearchCondition(new SearchCondition(workingCondition));
			workingCondition = workingCondition.getSearchCondition();
			return this;
		}

		public SearchBuilder not() {
			
			workingCondition.getBasicCondition().setNegate(true);
			workingCondition.getBasicCondition().setSearchCondition(new SearchCondition(workingCondition));
			
			workingNot = workingCondition;
			workingCondition = workingCondition.getBasicCondition().getSearchCondition();
			return this;
		}
		
		public SearchBuilder endNot() {
			if(workingNot == null) 
				throw new GedeonRuntimeException("Can't add 'endNot' param to query without call to 'not' first.");
			workingCondition = workingNot;
			workingNot = null;
			return this;
		}
		
		public SearchBuilder equals(String columnName, Object value) {
			
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.EQUALS,value));
			return this;
		}
		
		public SearchBuilder greaterThan(String columnName, Object value) {
			
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.GREATERTHAN,value));
			return this;
		}
		
		public SearchBuilder greaterThanOrEquals(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.GREATERTHANOREQUALS,value));
			return this;
		}
		
		public SearchBuilder lowerThan(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.LOWERTHAN,value));
			return this;
		}
		
		public SearchBuilder lowerThanOrEquals(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.LOWERTHANOREQUALS,value));
			return this;
		}
		
		public SearchBuilder like(String columnName, Object value) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.LIKE,value));
			return this;
		}
		
		public SearchBuilder between(String columnName, Object minValue, Object maxValue) {
			
			return this;
		}
		
		public SearchBuilder in(String columnName,Object... values ) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.IN,values));
			return this;
		}
		
		public SearchBuilder isNull(String columnName) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.ISNULL));
			return this;
		}
		
		public SearchBuilder isNotNull(String columnName) {
			workingCondition.getBasicCondition().setPredicate(
					new SearchParam(columnName,PredicateOperator.ISNOTNULL));
			return this;
		}
	}
}
