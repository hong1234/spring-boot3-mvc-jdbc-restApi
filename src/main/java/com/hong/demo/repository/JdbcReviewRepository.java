package com.hong.demo.repository;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Date;
import java.sql.Timestamp;

import java.util.*;
import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.dao.EmptyResultDataAccessException;

import com.hong.demo.domain.Review;
import com.hong.demo.exceptions.ResourceNotFoundException;
import com.hong.demo.domain.LikeStatus;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
public class JdbcReviewRepository implements ReviewRepository { 

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ReviewRowMapper reviewRowMapper;

    @Override
    public Review getReviewById(Integer reviewId){
        String sql = "select * from reviews where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("id", reviewId);
        Review review = jdbcTemplate.queryForObject(sql, parameters, reviewRowMapper);
        if(review == null)
            throw new ResourceNotFoundException("review with Id="+reviewId.toString()+" not found");
        return review;
    }

    @Override
    public List<Review> getReviewsOfBook(Integer bookId){
        String sql = "select * from reviews where book_id = :bookId";
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("bookId", bookId);
        return jdbcTemplate.query(sql, parameters, reviewRowMapper);
    }

    @Override
    public Review addReview(Review review) {
        String sql = """
                insert into reviews (book_id, name, email, content, like_status, created_on) values 
                (:bookId, :name, :email, :content, :likeStatus, :createdOn)
                """;
        
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("bookId", review.getBookId())
		.addValue("name", review.getName())
        .addValue("email", review.getEmail())
		.addValue("content", review.getContent())
        // .addValue("likeStatus", review.getLikeStatus().toString())
        .addValue("likeStatus", review.getLikeStatus().name())
        .addValue("createdOn", Timestamp.valueOf(review.getCreatedOn()));

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        
        // jdbcTemplate.update(String sql, SqlParameterSource paramSource);
        // jdbcTemplate.update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder);
        jdbcTemplate.update(sql, parameters, generatedKeyHolder);

        Number key = generatedKeyHolder.getKey();
        return getReviewById(key.intValue());
    }

    @Override
    public void deleteById(Integer reviewId) {
        String sql = "delete from reviews where id = :id";
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("id", reviewId);
        jdbcTemplate.update(sql, parameters);
    }

    @Override
    public void deleteViewsOfBook(Integer bookId) {
        String sql = "delete from reviews where book_id = :bookId";
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("bookId", bookId);
        jdbcTemplate.update(sql, parameters);
    }

}