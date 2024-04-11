package com.hong.demo.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String errorMessage) {
        super(errorMessage);
    }
}
