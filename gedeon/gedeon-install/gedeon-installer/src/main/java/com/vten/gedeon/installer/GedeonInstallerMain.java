package com.vten.gedeon.installer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.yaml.snakeyaml.scanner.ScannerException;

import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.apiimpl.GedFactoryImpl;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.utils.SaveMode;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vten.gedeon"})
public class GedeonInstallerMain {
	
	private static final Logger LOG = LoggerFactory.getLogger(GedeonInstallerMain.class);
	
	//Bean to manage installation
	private GedeonInstallerManager installManager;
	
	/*@Value("classpath:gedeon.json")
	private Resource resource;*/
	
	@Autowired
	GedFactoryImpl factory;

	public static void main(String[] args) {
		SpringApplication.run(GedeonInstallerMain.class, args);
	}
	
	@Autowired
	public GedeonInstallerMain(GedeonInstallerManager manager) {
		Assert.notNull(manager,"COGInstallerManager can't be null.");
		installManager = manager;
		
	}
	
	/**
	 * Launch after bean creation
	 * Parse Json inside resource file 'gedeon.json' and init database with
	 * the information
	 */
	@PostConstruct
	protected void run() {
		try {
			
			GedeonCollection collection = factory.createGedCollection("SII");
			collection.save(SaveMode.REFRESH);
			/*JsonParser springParser = JsonParserFactory.getJsonParser();
			Map<String,Object> json = springParser.parseMap(asString(resource));
			installManager.initDBFromJson(json);*/
		} catch(JsonParseException|ScannerException e) {
			LOG.error(GedeonErrorCode.OE3001.toString(),e);
		}
	}

	private String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
