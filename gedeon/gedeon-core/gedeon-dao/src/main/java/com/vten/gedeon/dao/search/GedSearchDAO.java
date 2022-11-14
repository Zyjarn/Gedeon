package com.vten.gedeon.dao.search;

import java.util.List;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.search.GedSearch;

public interface GedSearchDAO {
	
	/**
	 * Perform the given GedSearch on the implemented connector
	 * and return result as list of PersistableObject
	 * @param search
	 * @return
	 */
	public List<PersistableObject> search(GedSearch search);
	
	/**
	 * Perform the given GedSearch on the implemented connector
	 * and return result as list
	 * @param search
	 * @return
	 */
	public int searchCount(GedSearch search);
	
}
