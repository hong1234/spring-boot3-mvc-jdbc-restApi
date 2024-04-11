package com.hong.demo.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ErrorDetails {

	private HttpStatus status;
    private String message;
    private Map<String, Object> additionalData = new HashMap<>();
 
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Map<String, Object> getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(Map<String, Object> additionalData) {
		this.additionalData = additionalData;
	}

}
