package com.hong.demo.exceptions;

// import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

// @Slf4j
// @ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorDetails resourceNotFoundException(ResourceNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ExceptionHandler(ValidationException.class)
    public ErrorDetails validationException(ValidationException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ExceptionHandler
    public ErrorDetails appException(Exception e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

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
