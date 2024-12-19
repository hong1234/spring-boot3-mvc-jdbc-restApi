package com.hong.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.hong.demo.domain.Image;
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
        // book.setImages(bookRepository.getImagesOfBook(bookId));
        // return book;

        return bookRepository.findById(bookId);
    }

    @Transactional
    public Book addBook(Book book){
        return bookRepository.addBook(book);
    }

    public Book updateBook(Integer bookId, Book book){
        Book storedBook = bookRepository.findById(bookId);
        storedBook.setTitle(book.getTitle());
        storedBook.setContent(book.getContent());
        storedBook.setUpdatedOn(LocalDateTime.now());
        return bookRepository.updateBook(storedBook);
    }

    @Transactional
    public void deleteBook(Integer bookId){
        bookRepository.findById(bookId);
        // reviewRepository.deleteViewsOfBook(bookId);
        bookRepository.deleteById(bookId);
    }

    public List<Review> getBookReviews(Integer bookId){
        // return reviewRepository.getReviewsOfBook(bookId);
        Book book = bookRepository.findById(bookId);
        return book.getReviews();
    }

    public Review addReviewToBook(Integer bookId, Review review){
        bookRepository.findById(bookId);
        review.setBookId(bookId);
        return reviewRepository.addReview(review);
    }

    public void deleteReview(Integer reviewId){
        reviewRepository.deleteById(reviewId);
    }  

    public Image addImageToBook(Integer bookId, Image image){
        bookRepository.findById(bookId);
        image.setBookId(bookId);
        return bookRepository.addImage(image);
    }

}
