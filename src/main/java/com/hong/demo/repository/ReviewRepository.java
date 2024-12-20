package com.hong.demo.repository;

// import java.util.*;
import java.util.List;

import com.hong.demo.domain.Review;

public interface ReviewRepository {

    List<Review> getReviewsOfBook(Integer bookId);

    Review getReviewById(Integer id);
    
    Review addReview(Review review);
    
    void deleteById(Integer id);

    void deleteViewsOfBook(Integer bookId);
}


