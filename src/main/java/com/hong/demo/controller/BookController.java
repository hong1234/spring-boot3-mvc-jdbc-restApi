package com.hong.demo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Date;
import java.util.Optional;

// import java.net.URI;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


import com.hong.demo.domain.Review;
import com.hong.demo.domain.ReviewDto;
import com.hong.demo.domain.Book;
import com.hong.demo.domain.LikeStatus;

import com.hong.demo.repository.ReviewRepository;
import com.hong.demo.repository.BookRepository;
import com.hong.demo.service.BookService;

import com.hong.demo.exceptions.ResourceNotFoundException;
import com.hong.demo.exceptions.ValidationException;
import com.hong.demo.exceptions.ErrorDetails;


@RestController
@AllArgsConstructor
@RequestMapping(path=BookController.CONTROLLER_PATH, produces="application/json")
@CrossOrigin(origins = {"http://localhost:3000", "http://uicloud.com"})
public class BookController {

    public static final String CONTROLLER_PATH = "/api";

    private final BookService bookService;

    // books ---

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Book> getAllBooks(){
        return bookService.bookList();
    }

    @GetMapping("/books/search")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Book> searchBooksByTitle(@RequestParam String title){
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@PathVariable("bookId") Integer bookId){
        return bookService.getBookById(bookId);
    }

    // @PostMapping(consumes="application/json")
    // @ResponseStatus(HttpStatus.CREATED)
    // public Book createBook(@Valid @RequestBody Book book, Errors errors){ 
    //     if (errors.hasErrors()) 
    //         throw new ValidationException(createErrorString(errors));   
    //     return bookService.addBook(book);
    // }

    @PostMapping(path="/books", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book){ 
        return bookService.addBook(book);
    }

    // @PutMapping(path="/{bookId}", consumes="application/json")
    // @ResponseStatus(HttpStatus.OK)
    // public Book updateBook(@PathVariable("bookId") Integer bookId, @Valid @RequestBody Book book, Errors errors){
    // 	if(errors.hasErrors())
    //         throw new ValidationException(createErrorString(errors));
    //     return bookService.updateBook(bookId, book);
    // }

    @PutMapping(path="/books/{bookId}", consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable("bookId") Integer bookId, @Valid @RequestBody Book book){
        return bookService.updateBook(bookId, book);
    }

    @DeleteMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId") Integer bookId){
        bookService.deleteBook(bookId);
    }

    // reviews ---

    @GetMapping("/reviews/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getReviewsOfBook(@PathVariable("bookId") Integer bookId){
    	return bookService.getBookReviews(bookId);
    }

    // @PostMapping(path="/reviews/{bookId}", consumes="application/json")
    // @ResponseStatus(HttpStatus.CREATED)
    // public Review createBookReview(@PathVariable("bookId") Integer bookId, @Valid @RequestBody Review review, Errors errors){
    //     // if(errors.hasErrors())
    //     //     throw new ValidationException(createErrorString(errors));
    //     return bookService.addReviewToBook(bookId, review);
    // }

    // @PostMapping(path="/reviews/{bookId}", consumes="application/json")
    // @ResponseStatus(HttpStatus.CREATED)
    // public Review createBookReview(@PathVariable("bookId") Integer bookId, @Valid @RequestBody Review review){
    //     return bookService.addReviewToBook(bookId, review);
    // }

    private Review getReview(ReviewDto reviewDto){
        Review review = new Review();
        review.setName(reviewDto.getName());
        review.setEmail(reviewDto.getEmail());
        review.setContent(reviewDto.getContent());
        review.setLikeStatus(LikeStatus.valueOf(reviewDto.getLikeStatus()));
        return review;
    }

    @PostMapping(path="/reviews/{bookId}", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReviewToBook(@PathVariable("bookId") Integer bookId, @Valid @RequestBody ReviewDto reviewDto){
        Review review = getReview(reviewDto);
        return bookService.addReviewToBook(bookId, review);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookReview(@PathVariable("reviewId") Integer reviewId){
        bookService.deleteReview(reviewId);
    }

    
    private String createErrorString(Errors result){
        StringBuilder sb =  new StringBuilder();
        result.getAllErrors().forEach(error -> {
            if(error instanceof FieldError) {
                FieldError err= (FieldError) error;
                sb.append(err.getField()).append(" ").append(err.getDefaultMessage());
            }
        });
        return sb.toString();
    }

    // @ExceptionHandler
    // public ResponseEntity<?> bindingException(ValidationException e){
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    //     errorDetails.setMessage(e.getMessage());
    //     return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    
}
