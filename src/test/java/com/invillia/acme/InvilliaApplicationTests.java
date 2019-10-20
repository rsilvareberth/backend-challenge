package com.invillia.acme;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InvilliaApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void createStoreTest() {
		// Call the REST API
		String request = "{\n" + 
				"	\"name\": \"store1\",\n" + 
				"	\"address\":[\n" + 
				"	   {\n" + 
				"	      \"fulladdress\": \"rua1\",\n" + 
				"	      \"city\": \"Recife\",\n" + 
				"	      \"state\": \"Pernambuco\",\n" + 
				"	      \"zip\": \"509322-000\",\n" + 
				"	      \"country\": \"Brasil\"\n" + 
				"	   }\n" + 
				"	]\n" + 
				"}";
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/store", request, String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));

	}
	@Test
	public void createdStoreTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/store/name/store1", String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		String responsePlain = response.getBody();
		assertTrue(responsePlain.contains("store1"));
		
	}

}
