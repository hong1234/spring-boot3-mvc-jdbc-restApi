package com.hong.demo.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
