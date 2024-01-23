package com.spring.nesl_api;

import com.spring.nesl_api.service.NeslService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
class NeslApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	NeslService neslService;

	@Test
	public void testGetNeslController() throws Exception {
		// Perform the GET request and expect a 200 OK status
		mockMvc.perform(MockMvcRequestBuilders.get("/api/nesl/get-nesl-api")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(""));
	}

	@Test
	public void testGetNeslService(){
		String expectedResponse = null;
		assertEquals(expectedResponse, neslService.getNeslApi());
	}

}
