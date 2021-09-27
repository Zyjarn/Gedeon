package com.vten.gedeon.ws;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vten.gedeon.api.PersistableObject;
import com.vten.gedeon.api.property.Properties;
import com.vten.gedeon.api.utils.OEConstants;
import com.vten.gedeon.apiimpl.property.PropertiesImpl;
import com.vten.gedeon.apiimpl.property.PropertyImpl;
import com.vten.gedeon.dao.PersistableObjectDAOImpl;
import com.vten.gedeon.utils.SaveMode;

import lombok.Data;

@RestController
public class COGRestController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	PersistableObjectDAOImpl dao;
	
	@GetMapping("/getObject")
	public PersistableObject getObject(@RequestParam(value = "className",required = true) String className,
			@RequestParam(value = "id",required = true) String id ) {
		
		return dao.getObject(className, id);
	}
	
	@PostMapping(path = "/saveObject",consumes = MediaType.APPLICATION_JSON_VALUE)
	public int saveObject(@RequestParam(value = "className",required = true) String className,
			@RequestBody Map<String, Object> properties) {
			//@RequestParam(value = "properties") MapParam properties) {
		//TODO handle update
		PersistableObject obj = dao.getInstanceByClassName(className);
		Properties props = new PropertiesImpl();
		properties.entrySet().stream()
			.forEach(e -> props.add(new PropertyImpl(e.getKey(),e.getValue())));
		if(!properties.containsKey(OEConstants.PROP_CLASSNAME))
			props.add(new PropertyImpl(OEConstants.PROP_CLASSNAME,className));
		obj.setProperties(props);
		dao.saveObject(obj,SaveMode.NO_REFRESH);
		return 1;
	}
	@Data
	public class MapParam {   
		private Map<String, String> params;
	}
}