package com.vten.gedeon.dao.connector;

import com.vten.gedeon.api.search.OESearch;

public interface OEDBSearch {
	
	void search(final OESearch search);
    String format(final OESearch search);
}