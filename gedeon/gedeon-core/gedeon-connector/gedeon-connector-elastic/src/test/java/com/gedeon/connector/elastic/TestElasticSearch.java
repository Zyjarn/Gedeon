package com.gedeon.connector.elastic;

import com.vten.gedeon.api.search.OESearch;
import com.vten.gedeon.connector.elastic.OpenECMElasticSearch;

public class TestElasticSearch {
	
	private OpenECMElasticSearch searchManager;

	public void testSearch() throws ClassNotFoundException {
		searchManager = new OpenECMElasticSearch();
		OESearch search = new OESearch.OESearchBuilder().select("DocumentTitle").from("Document")
				.where()
					.equals("PropertyType", "STRING")
						.and()
							.not()
	 						.equals("Name","LastModifier")
							.or()
							.equals("Name","AddedBy")
						.endNot()
						.build();
		System.out.println(searchManager.format(search));
	}
	
	
	public static void main(String[] args) {
		try {
			new TestElasticSearch().testSearch();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
