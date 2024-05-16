package com.hong.demo.exceptions;

import java.util.Map;
import java.util.HashMap;
import org.springframework.validation.FieldError;

// import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;


// import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

// @Slf4j
// @ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)  // thrown by HttpMessageConverter implementation
    public ErrorDetails validationException(HttpMessageNotReadableException e) { 
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)  // thrown by HttpMessageConverter implementation
    public ErrorDetails validationException(HttpMessageNotWritableException e) { 
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDetails handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorDetails.setErrorDetails(errors);
        return errorDetails;
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorDetails resourceNotFoundException(ResourceNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ExceptionHandler
    public ErrorDetails otherExceptions(Exception e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    // @ExceptionHandler(ValidationException.class)
    // public ErrorDetails validationException(ValidationException e) {
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    //     errorDetails.setMessage(e.getMessage());
    //     return errorDetails;
    // }

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    //     Map<String, String> errors = new HashMap<>();
    //     ex.getBindingResult().getAllErrors().forEach((error) -> {
    //         String fieldName = ((FieldError) error).getField();
    //         String errorMessage = error.getDefaultMessage();
    //         errors.put(fieldName, errorMessage);
    //     });
        
    //     return errors;
    // }

    // @ExceptionHandler(SpringAppException.class)
    // public ErrorDetails servletRequestBindingException(SpringAppException e) {
    //     // log.error("SpringBlogException occurred: " + e.getMessage());
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    //     errorDetails.setMessage(e.getMessage());
    //     return errorDetails;
    // }

    // @ExceptionHandler(SpringAppException.class)
    // public String servletRequestBindingException(ServletRequestBindingException e) {
    //     // log.error("SpringBlogException occurred: " + e.getMessage());
    //     return "error";
    // }

    // @ExceptionHandler(SpringAppException.class)
    // public ResponseEntity<?> servletRequestBindingException(SpringAppException e) {
    //     // log.error("SpringBlogException occurred: " + e.getMessage());
        
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    //     errorDetails.setMessage(e.getMessage());
    //     return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    // }

}
