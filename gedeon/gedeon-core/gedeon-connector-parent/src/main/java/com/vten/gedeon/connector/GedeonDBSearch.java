package com.vten.gedeon.connector;

import com.vten.gedeon.api.search.GedSearch;

public interface GedeonDBSearch {

	/**
	 * Format the given search to a string representation
	 * @param search
	 * @return
	 */
	public String format(GedSearch search);
}
