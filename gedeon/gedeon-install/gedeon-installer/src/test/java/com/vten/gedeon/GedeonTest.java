package com.vten.gedeon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.apiimpl.GedFactoryImpl;
import com.vten.gedeon.utils.SaveMode;

@SpringBootTest
@SpringBootConfiguration
@ComponentScan(basePackages = "com.vten.gedeon")
class GedeonTest {

	@Autowired
	GedFactoryImpl factory;

	@Test
	void test() {
		GedeonCollection collection = factory.getGedCollection("SII2");
		Storage storage = factory.createStorage(collection);
		storage.setName("Storage1");
		storage.setStorageType("FILESYSTEM");
		storage.save(SaveMode.REFRESH);
	}

}
