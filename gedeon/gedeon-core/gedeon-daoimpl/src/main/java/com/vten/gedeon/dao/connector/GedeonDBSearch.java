package com.vten.gedeon.dao.connector;

import com.vten.gedeon.api.search.GedSearch;

public interface GedeonDBSearch {
	
	void search(final GedSearch search);
    String format(final GedSearch search);
}