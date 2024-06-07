package com.hong.demo;

import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Date;
// import java.util.List;
// import java.util.Collections;

import org.apache.commons.codec.binary.Base64;

// import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// https://www.baeldung.com/introduction-to-assertj
// import static org.assertj.core.api.Assertions.*; 
import static org.assertj.core.api.Assertions.assertThat;

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
// import com.hong.demo.exceptions.ErrorDetails;

// import org.skyscreamer.jsonassert.JSONAssert;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
// import java.io.IOException;
// import org.json.JSONObject;
// import org.json.JSONException;

// import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

// @SpringBootTest
// class MvcJdbcRestApiHApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}

// }

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class MvcJdbcRestApiHApplicationTests {

	// private final String ROOT_URL = "http://localhost:8000";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// @BeforeEach 
    // void init() {
    //     LOG.info("startup");
    //     list = new ArrayList<>(Arrays.asList("test1", "test2"));
    // }

    // @AfterEach
    // void teardown() {
    //     LOG.info("teardown");
    //     list.clear();
    // }

	// @BeforeAll
    // static void setup() {
	// 	   LOG.info("startup - creating DB connection");
	// }

	// @AfterAll
    // static void tearDown() {
    //     LOG.info("closing DB connection");
    // }

	private String getRootUrl(){
		return "http://localhost:"+port;
	}

	private String getBase64Credentials(String username, String password){
		String plainCredentials = username + ":" + password; // "autor:autor"
    	String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
		return base64Credentials;
	}

	// Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
	private HttpHeaders getHeaders2(){
    	String base64Credentials = getBase64Credentials("autor", "autor");

    	HttpHeaders headers = new HttpHeaders();
    	// headers.add("Authorization", "Basic " + base64Credentials);
		headers.set("Authorization", "Basic " + base64Credentials);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
    	return headers;
    }

	private HttpHeaders getHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("autor", "autor"); // username:password
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));		
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Test
	@Order(1)
	public void testAddBook() {
		Book book = new Book();
		book.setTitle("test book1");
		book.setContent("test book1");
		book.setCreatedOn(null);

		HttpHeaders requestHeaders = getHeaders();  

		HttpEntity<Book> request = new HttpEntity<Book>(book, requestHeaders);

		Book book2 = restTemplate.postForObject(getRootUrl()+"/api/books", request, Book.class);
		// System.out.println(book2);

		assertThat(book2.getTitle()).isEqualTo("test book1");
	}

	@Test
	@Order(2)
	public void testAddBook2() {

		Book book = new Book();
		book.setTitle("Exploring SpringBoot");
		book.setContent("SpringBoot was awesome!");
		book.setCreatedOn(null);

		HttpHeaders requestHeaders = getHeaders2();
		HttpEntity<Object> request = new HttpEntity<Object>(book, requestHeaders);

		ResponseEntity<String> response = restTemplate.exchange(
			getRootUrl()+"/api/books", 
			HttpMethod.POST, 
			request, 
			String.class
		);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		try {
			// System.out.println(response.getBody());
			// JSON to Java mapping code
			Book book2 = objectMapper.readValue(response.getBody(), Book.class);
			// System.out.println(book2);

			assertThat(book2.getTitle()).isEqualTo("Exploring SpringBoot");
			assertEquals("SpringBoot was awesome!", book2.getContent());

		} catch (JsonProcessingException e) {
			System.out.println("Error occurred during JSON processing: " + e.getMessage());
		} 

	}

	@Test
	@Order(3)
	public void testAddBook3() {

		Book book = new Book();
		book.setTitle("Exploring SpringBoot3");
		book.setContent("SpringBoot 3 is awesome!");
		book.setCreatedOn(null);

		HttpHeaders requestHeaders = getHeaders();

		HttpEntity<Book> request = new HttpEntity<Book>(book, requestHeaders);
		// HttpEntity<Object> request = new HttpEntity<Object>(book, requestHeaders);

		ResponseEntity<Book> response = restTemplate.exchange(
			getRootUrl()+"/api/books", 
			HttpMethod.POST, 
			request, 
			Book.class
		);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// System.out.println(response.getBody());
		Book book2 = response.getBody();

		assertThat(book2.getTitle()).isEqualTo("Exploring SpringBoot3");
		assertEquals("SpringBoot 3 is awesome!", book2.getContent());

	}

	@Test
	@Order(4)
	// @AfterAll // method must static
	public void testGetAllBooks(){	
		HttpHeaders headers = getHeaders();
		HttpEntity<?> entity = new HttpEntity<>(null, headers);
		// HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Iterable<Book>> response = restTemplate.exchange(
			getRootUrl()+"/api/books", 
			HttpMethod.GET, 
			entity, 
			new ParameterizedTypeReference<Iterable<Book>>(){}
		);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		// System.out.println(response.getBody());
		Iterable<Book> list = response.getBody();
		assertThat(list).hasSize(3);

		//-------------------

		// ResponseEntity<String> response = restTemplate.exchange(
		// 	getRootUrl()+"/api/books", 
		// 	HttpMethod.GET, 
		// 	entity, 
		// 	new ParameterizedTypeReference<String>(){}
		// );

		//-------------------
		// ResponseEntity<String> response = restTemplate
		// .withBasicAuth("autor", "autor")
		// .getForEntity(getRootUrl()+"/api/books", String.class);
		// System.out.println(response.getBody());
 
	}

	// @Test
	// public void testDeleteBook(){
	// 	String bookId = "5";
	// 	HttpHeaders requestHeaders = getHeaders();
	// 	HttpEntity<String> request = new HttpEntity<>(requestHeaders);
		// ResponseEntity<ErrorDetails> responseEntity = restTemplate.exchange(
		// // getRootUrl()+
		// "/api/books/"+bookId, 
		// HttpMethod.DELETE, 
		// request, 
		// ErrorDetails.class);
	// 	assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	// }

}
