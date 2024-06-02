package com.hong.demo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
// import java.lang.String;

import org.junit.jupiter.api.Test;
import org.apache.commons.codec.binary.Base64;
import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.*; // https://www.baeldung.com/introduction-to-assertj

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import com.hong.demo.domain.Book;
import com.hong.demo.exceptions.ErrorDetails;

// import org.skyscreamer.jsonassert.JSONAssert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

// @SpringBootTest
// class MvcJdbcRestApiHApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}

// }


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MvcJdbcRestApiHApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	/*
    * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
    */
	private static HttpHeaders getHeaders2(){   // header for Basic-Authentication
    	String plainCredentials= "autor:autor"; // name:password
    	String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
    	
    	HttpHeaders headers = new HttpHeaders();
        
    	// headers.add("Authorization", "Basic " + base64Credentials);
		headers.set("Authorization", "Basic " + base64Credentials);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
    	return headers;
    }

	private HttpHeaders getHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("autor", "autor"); // name:password
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));		
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	private String getRootUrl(){
		return "http://localhost:"+port;
	}

	@Test
	public void testCreateBook(){

		Book book = new Book();
		book.setTitle("Exploring SpringBoot3");
		book.setContent("SpringBoot 3 is awesome!");
		book.setCreatedOn(null);

		// HttpHeaders requestHeaders = getHeaders2();
		// HttpEntity<Object> request = new HttpEntity<Object>(book, requestHeaders);

		HttpHeaders requestHeaders = getHeaders();
		HttpEntity<Book> request = new HttpEntity<Book>(book, requestHeaders);

		ResponseEntity<String> response = restTemplate.exchange(
			getRootUrl()+"/api/books", 
			HttpMethod.POST, 
			request, 
			String.class
		);

		// System.out.println(response.getBody());
		assertThat(response.getStatusCodeValue()).isEqualTo(201);

		try {
			// JSON to Java mapping code
			Book book2 = objectMapper.readValue(response.getBody(), Book.class);
			assertThat(book2.getTitle()).isEqualTo("Exploring SpringBoot3");
		}  catch (JsonProcessingException e) {
			System.out.println("Error occurred during JSON processing: " + e.getMessage());
		}

		// try {
		// 	JSONObject jo = new JSONObject(response.getBody());
		// 	assertThat(jo.getString("title")).isEqualTo("Exploring SpringBoot3");
		// } catch (JSONException e) {
		// 	System.out.println("Error occurred during JSON parsing: " + e.getMessage());
		// } 

	}

	@Test
	public void testGetAllBooks(){	
		HttpHeaders headers = getHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(
			getRootUrl()+"/api/books", 
			HttpMethod.GET, 
			entity, 
			String.class
		);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).hasSizeGreaterThan(0);
	}

	// @Test
	// public void testDeleteBook(){
	// 	String bookId = "5";
	// 	HttpHeaders requestHeaders = getHeaders();
	// 	HttpEntity<String> request = new HttpEntity<>(requestHeaders);
	// 	ResponseEntity<ErrorDetails> responseEntity = restTemplate.exchange(getRootUrl()+"/api/books/"+bookId, HttpMethod.DELETE, request, ErrorDetails.class);
	// 	assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	// }

}
