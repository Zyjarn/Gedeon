package com.vten.gedeon.presentation.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vten.gedeon.rendition.controller.GedeonRenditionController;

@WebAppConfiguration
@ContextConfiguration(classes = { GedeonRenditionController.class })
@SpringBootTest
class GedeonRenditionControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	void uploadMultipartFile_whenPostWithRequestPart_thenReturnsOK() throws Exception {
		try (InputStream stream = new FileInputStream("src/test/resources/testupload.pdf")) {

			MockMultipartFile fileUpload = new MockMultipartFile("file", null, "application/pdf", stream);

			MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
			mockMvc.perform(multipart("/upload").file(fileUpload).param("filename", "testupload.pdf").param("mimetype",
					"application/pdf")).andExpect(status().isOk());
		}
	}
}
