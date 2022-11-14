package com.vten.gedeon.ws;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vten.gedeon.api.GedFactory;
import com.vten.gedeon.api.GedeonCollection;
import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.Property;
import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.utils.SaveMode;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ApiOperation("Gedeon API")
public class GedeonRestController {

	@Autowired
	private GedFactory factory;

	@GetMapping("/getObject")
	public PersistableObject getObject(@RequestParam(value = "collection", required = true) String collectionName,
			@RequestParam(value = "className", required = true) String className,
			@RequestParam(value = "id", required = true) String id) {
		GedeonCollection collection = factory.getGedCollection(collectionName);
		return factory.getGedDocument(collection, new GedId(id));
	}

	@PostMapping(path = "/saveObject", consumes = MediaType.APPLICATION_JSON_VALUE)
	public int saveObject(@RequestParam(value = "collection", required = true) String collectionName,
			@RequestParam(value = "className", required = true) String className,
			@RequestBody Map<String, Object> properties) {
		long start = System.currentTimeMillis();
		log.info("Start");
		int result = -1;
		try {
			GedeonCollection collection = factory.getGedCollection(collectionName);

			PersistableObject obj = factory.createGedDocument(collection, className);
			properties.entrySet().stream()
					.forEach(e -> obj.getProperties().add(new Property(e.getKey(), e.getValue())));
			obj.save(SaveMode.NO_REFRESH);
			result = 1;
		} catch (GedeonRuntimeException e) {
			log.error("Gedeon runtime issue while saving doc.", e);
		} catch (RuntimeException e) {
			log.error("Unexpected issue while saving doc.", e);
		}

		log.info("End, {}ms", System.currentTimeMillis() - start);

		return result;
	}

	@Data
	public class MapParam {
		private Map<String, String> params;
	}
}