package com.hong.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import com.hong.demo.domain.Book;
import com.hong.demo.domain.Review;
import com.hong.demo.domain.LikeStatus;

import java.time.LocalDateTime;

public class BookMapExtractor implements ResultSetExtractor<Book> {
    @Override
    public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
        Book book = null;
        Map<String, Review> reviewMap = new HashMap<>();

        while (rs.next()) {
            if (book == null) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setContent(rs.getString("content"));
                book.setCreatedOn(convertToLocalDateTime(rs.getTimestamp("created_on")));
                book.setUpdatedOn(convertToLocalDateTime(rs.getTimestamp("updated_on")));
            }

            if (rs.getString("review_id") != null){
                Review review = new Review();
                review.setId(rs.getInt("review_id"));
                review.setName(rs.getString("name"));
                review.setEmail(rs.getString("email"));
                review.setContent(rs.getString("content2"));
                review.setLikeStatus(LikeStatus.valueOf(rs.getString("like_status")));
                review.setCreatedOn(convertToLocalDateTime(rs.getTimestamp("created_on2")));
                review.setUpdatedOn(convertToLocalDateTime(rs.getTimestamp("updated_on2")));
                review.setBookId(book.getId());

                reviewMap.put(Integer.toString(review.getId()), review);
            }
        }

        if(book != null){
            List<Review> reviews = new ArrayList<>(reviewMap.values());
            book.setReviews(reviews);
        }
        return book;
    }

    private LocalDateTime convertToLocalDateTime(Timestamp tst) {
        if (tst == null) {
            return null;
        } else {
            return tst.toLocalDateTime();
        }
    }
}


