package com.vten.gedeon.installer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.annotation.ComponentScan;
import org.yaml.snakeyaml.scanner.ScannerException;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.apiimpl.GedFactoryImpl;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.utils.SaveMode;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan(basePackages = { "com.vten.gedeon" })
@Slf4j
public class GedeonInstallerMain {

	@Autowired
	GedFactoryImpl factory;

	public static void main(String[] args) {
		SpringApplication.run(GedeonInstallerMain.class, args);
	}

	/**
	 * Launch after bean creation Parse Json inside resource file 'gedeon.json' and
	 * init database with the information
	 */
	@PostConstruct
	protected void run() {
		try {

			GedeonCollection collection = factory.getGedCollection("SII2");
			if (collection == null) {
				collection = factory.createGedCollection("SII2");
				collection.save(SaveMode.REFRESH);
			}
		} catch (JsonParseException | ScannerException e) {
			log.error(GedeonErrorCode.OE3001.toString(), e);
		}
	}

}
