package com.shop.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTest {

	@LocalServerPort
	private int port;
	private final TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testSwaggerConnection() {
		// Make a GET request to your Swagger-documented endpoint
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/shop-service/swagger-ui/index.html", String.class);

		// Assert the response status code and content as needed
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("swagger"));
	}
}
