package com.hong.demo.repository;

// import java.util.*;
import java.util.List;

import com.hong.demo.domain.Review;

public interface ReviewRepository {

    Review findById(Integer id);
    // Iterable<Review> findAll();
    
    Review addReview(Review review);
    
    void deleteById(Integer id);

    List<Review> getReviewsOfBook(Integer bookId);
    
    void deleteViewsOfBook(Integer bookId);
}


