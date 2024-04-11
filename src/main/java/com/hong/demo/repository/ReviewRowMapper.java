package com.hong.demo.repository;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.sql.Date;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hong.demo.domain.Review;
import com.hong.demo.domain.LikeStatus;

@Component
public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        
        review.setBookId(rs.getInt("book_id"));
        review.setId(rs.getInt("id"));
        review.setName(rs.getString("name"));
        review.setEmail(rs.getString("email"));
        review.setContent(rs.getString("content"));
        review.setLikeStatus(LikeStatus.valueOf(rs.getString("like_status")));
        review.setCreatedOn(convertToLocalDateTime(rs.getTimestamp("created_on")));
        review.setUpdatedOn(convertToLocalDateTime(rs.getTimestamp("updated_on")));
        return review;
    }

    private LocalDateTime convertToLocalDateTime(Timestamp tst) {
        if (tst == null) {
            return null;
        } else {
            return tst.toLocalDateTime();
        }
    }
}


