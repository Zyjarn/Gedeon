package com.vten.gedeon.ws;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vten.gedeon.ws.entities.ObjectProperty;
import com.vten.gedeon.ws.entities.RequestGetObject;
import com.vten.gedeon.ws.entities.ResponseDelete;
import com.vten.gedeon.ws.entities.ResponseGetObject;
import com.vten.gedeon.ws.entities.Status;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/document")
@ApiOperation("Gedeon API")
public class DocumentRestController {

	@DeleteMapping("/id/{id}/delete")
	public ResponseDelete deleteObject(
			@PathVariable(value = "id") String id,
			@RequestParam(value = "collection") String collection,
			@RequestParam(value = "allVersions",required = false, defaultValue = "false") Boolean allVersions){

		ResponseDelete response = new ResponseDelete();
		Status status = new Status("GWS000","No Error");
		response.setStatus(status);
		
		return response;
	}


	@GetMapping("/id/{id}")
	public ResponseGetObject getObject(
			@PathVariable(value = "id") String id,
			@RequestBody(required = false) RequestGetObject request){

		ResponseGetObject response = new ResponseGetObject();
		Status status = new Status("GWS000","No Error");
		response.setStatus(status);

		ObjectProperty propertyName = new ObjectProperty();
		propertyName.setName("Name");
		propertyName.setType("string");
		propertyName.setValue("Test");
		response.getProperties().put("Name",propertyName);
		
		return response;
	}


	@PostMapping(path = "/saveObject", consumes = MediaType.APPLICATION_JSON_VALUE)
	public int saveObject(@RequestParam(value = "collection", required = true) String collectionName,
			@RequestParam(value = "className", required = true) String className,
			@RequestBody Map<String, Object> properties) {
		/*long start = System.currentTimeMillis();
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

		return result;*/
		return 1;
	}

}