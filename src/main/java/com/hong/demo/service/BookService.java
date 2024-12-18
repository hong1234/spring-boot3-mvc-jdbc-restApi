package com.hong.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.hong.demo.domain.Book;
import com.hong.demo.domain.Review;
import com.hong.demo.repository.BookRepository;
import com.hong.demo.repository.ReviewRepository;

import com.hong.demo.exceptions.ResourceNotFoundException;
import com.hong.demo.exceptions.SpringAppException;
import com.hong.demo.exceptions.ErrorDetails;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;


// @Slf4j 
@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public Iterable<Book> bookList(){
        return bookRepository.findAll();
    }

    public Iterable<Book> searchBooksByTitle(String title){
        return bookRepository.searchByTitle(title);
    }

    public Book getBookById(Integer bookId){
        // Book book = bookRepository.findById(bookId);
        // book.setReviews(reviewRepository.getReviewsOfBook(bookId));
        // return book;
        return bookRepository.findById(bookId);
    }

    public Book addBook(Book book){
        return bookRepository.addBook(book);
    }

    public Book updateBook(Integer bookId, Book book){
        Book storedBook = bookRepository.findById(bookId);
        if(storedBook != null){
            storedBook.setTitle(book.getTitle());
            storedBook.setContent(book.getContent());
            storedBook.setUpdatedOn(LocalDateTime.now());
            return bookRepository.updateBook(storedBook);
        } else {
            throw new ResourceNotFoundException("Book with Id="+bookId+" not found");
        }
    }

    @Transactional
    public void deleteBook(Integer bookId){
        Book book = bookRepository.findById(bookId);
        if(book != null){
            reviewRepository.deleteViewsOfBook(bookId);
            bookRepository.deleteById(bookId);
        } else {
            throw new ResourceNotFoundException("Book with Id="+bookId+" not found");
        }
    }

    public List<Review> getBookReviews(Integer bookId){
        return reviewRepository.getReviewsOfBook(bookId);
    }

    public Review addReviewToBook(Integer bookId, Review review){
        Book book = bookRepository.findById(bookId);
        if(book != null){
            return reviewRepository.addReview(bookId, review);
        } else {
            throw new ResourceNotFoundException("Book with Id="+bookId+" not found");
        }
    }

    public void deleteReview(Integer reviewId){
        reviewRepository.deleteById(reviewId);
    }  

}
