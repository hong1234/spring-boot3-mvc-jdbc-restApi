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
import com.hong.demo.domain.LikeStatus;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
public class JdbcReviewRepository implements ReviewRepository { 

    private static final String SQL_QUERY_FIND_ALL = "select * from reviews";
    private static final String SQL_QUERY_FIND_BY_ID = SQL_QUERY_FIND_ALL + " where id = :id";
    private static final String SQL_QUERY_FIND_BY_BOOKID = SQL_QUERY_FIND_ALL + " where book_id = :bookId";

    private static final String SQL_INSERT = "insert into reviews (book_id, name, email, content, like_status, created_on) values (:bookId, :name, :email, :content, :likeStatus, :createdOn)";
    // private static final String SQL_UPDATE = "update reviews set name = :name, email = :email, content = :content, updated_on = :updated_on where id = :id";
    private static final String SQL_DELETE = "delete from reviews where id = :id";
    private static final String SQL_DELETE_BOOK_REVIEWS = "delete from reviews where book_id = :bookId";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ReviewRowMapper reviewRowMapper;

    @Override
    public Review findById(Integer reviewId){
        try {
            SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", reviewId);
            // jdbcTemplate.queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
            return jdbcTemplate.queryForObject(SQL_QUERY_FIND_BY_ID, parameters, reviewRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Review> getReviewsOfBook(Integer bookId){
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("bookId", bookId);
        return jdbcTemplate.query(SQL_QUERY_FIND_BY_BOOKID, parameters, reviewRowMapper);
    }

    @Override
    public Review addReview(Integer bookId, Review review) {
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("bookId", bookId)
		.addValue("name", review.getName())
        .addValue("email", review.getEmail())
		.addValue("content", review.getContent())
        // .addValue("likeStatus", review.getLikeStatus().toString())
        .addValue("likeStatus", review.getLikeStatus().name())
        .addValue("createdOn", Timestamp.valueOf(review.getCreatedOn()));

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        
        // jdbcTemplate.update(String sql, SqlParameterSource paramSource);
        // jdbcTemplate.update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder);
        jdbcTemplate.update(SQL_INSERT, parameters, generatedKeyHolder);

        Number key = generatedKeyHolder.getKey();
        return findById(key.intValue());
    }

    @Override
    public void deleteById(Integer reviewId) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", reviewId);
        jdbcTemplate.update(SQL_DELETE, parameters);
    }

    @Override
    public void deleteViewsOfBook(Integer bookId){
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("bookId", bookId);
        jdbcTemplate.update(SQL_DELETE_BOOK_REVIEWS, parameters);
    }

}