package com.vten.gedeon.dao.connector;

import com.vten.gedeon.api.search.OESearch;

public interface OEDBSearch {
	
	/**
	 * Format the given search to the corresponding DB language
	 * @return the query matching database syntax
	 */
	public String format(OESearch search);


}
