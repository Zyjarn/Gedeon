package com.vten.gedeon.webapp.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class CollectionController {
	
	@GetMapping(value = "/test")
	public String test() {
		return "ABC";
	}

}
