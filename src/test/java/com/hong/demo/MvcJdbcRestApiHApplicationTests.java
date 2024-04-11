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

	/*
    * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
    */
	private static HttpHeaders getHeaders(){  // header for Basic-Authentication
    	String plainCredentials="admin:admin"; // admin  name:password
    	String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
    	
    	HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.add("Authorization", "Basic " + base64Credentials);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
    }

	private String getRootUrl(){
		return "http://localhost:"+port;
	}

	@Test
	public void testGetAllBooks(){	
		// HttpHeaders requestHeaders = new HttpHeaders();
		ResponseEntity<List<Book>> response = restTemplate.exchange(getRootUrl()+"/api/books", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		// assertThat(response.getBody()).hasSizeGreaterThan(0);
	}

	@Test
	public void testCreateBook(){

		Book book = new Book();
		book.setTitle("Exploring SpringBoot 3");
		book.setContent("SpringBoot 3 is awesome!");

		// HttpHeaders requestHeaders = new HttpHeaders();
		HttpHeaders requestHeaders = getHeaders();
    	requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
        // HttpEntity<Object> request = new HttpEntity<Object>(book, getHeaders());
		HttpEntity<Object> request = new HttpEntity<Object>(book, requestHeaders);

		// HttpEntity<String> request = new HttpEntity<>("""
		// 	{
		// 		"title": "TEST2",
		// 		"content": "TEST2"
		// 	}
		// """, requestHeaders);

        ResponseEntity<Book> bookResponse = restTemplate.exchange(getRootUrl()+"/api/books", HttpMethod.POST, request, Book.class);
        	
		assertThat(bookResponse.getStatusCodeValue()).isEqualTo(200);

		// assertNotNull(bookResponse);
        // assertNotNull(bookResponse.getBody());
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
