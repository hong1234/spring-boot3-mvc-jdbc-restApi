package com.hong.demo.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ErrorDetails {

	private HttpStatus status;
	private Map<String, String> errorDetails = new HashMap<>();
	// private String message;
	// private Map<String, Object> additionalData = new HashMap<>();
 
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Map<String, String> getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(Map<String, String> errorDetails) {
		this.errorDetails = errorDetails;
	}

	public void setMessage(String message) {
		errorDetails.put("exception", message);
	}

	// public String getMessage() {
	// 	return message;
	// }
	// public void setMessage(String message) {
	// 	this.message = message;
	// }

}
