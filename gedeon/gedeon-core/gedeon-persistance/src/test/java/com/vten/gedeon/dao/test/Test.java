package com.vten.gedeon.dao.test;


import org.springframework.boot.test.context.SpringBootTest;

import com.vten.gedeon.api.search.OESearch;

@SpringBootTest
public class Test {

	public void test() {
		OESearch ser = new OESearch.OESearchBuilder().select("DocumentTitle").from("Document")
			.where()
				.equals("dmsSource", "webapp")
					.and()
						.not()
 						.isNull("dmsType")
						.or()
						.isNull("dmsNature")
					.endNot()
					.build();
	}
}
