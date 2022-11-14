package com.vten.gedeon.rendition.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ApiOperation("Gedeon Rendition")
public class GedeonRenditionController {

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveObject(@RequestParam String filename, @RequestParam String mimetype,
			@RequestPart MultipartFile file) {
		long start = System.currentTimeMillis();
		log.info("Start");
		log.info("filename={},mimetype={},size={}", filename, mimetype, file.getSize());

		log.info("End, {}ms", System.currentTimeMillis() - start);

		return ResponseEntity.ok().build();
	}

}