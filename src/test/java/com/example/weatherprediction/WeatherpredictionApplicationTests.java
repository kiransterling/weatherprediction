package com.example.weatherprediction;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherpredictionApplicationTests {

	
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testGetTemperaturePositive() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/London"), HttpMethod.GET,
				entity, String.class);


		String expectedResponseCode = "200 OK";

		assertEquals(expectedResponseCode,response.getStatusCode().toString());

	}
	
	@Test
	public void testGetTemperatureNegetive() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/London123"), HttpMethod.GET,
				entity, String.class);
		

		String expectedResponseCode = "404 NOT_FOUND";

		assertEquals(expectedResponseCode,response.getStatusCode().toString());

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}


}
