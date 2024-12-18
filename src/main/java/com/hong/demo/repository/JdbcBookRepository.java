package com.hong.demo.repository;

// import lombok.RequiredArgsConstructor;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Date;
import java.sql.Timestamp;

import java.util.*;
// import java.util.Set;

import org.springframework.stereotype.Repository;

// import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import com.hong.demo.domain.Book;
import com.hong.demo.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;


@Repository
@AllArgsConstructor
public class JdbcBookRepository implements BookRepository { 

    private static final String SQL_QUERY_FIND_ALL = "select * from books";
    private static final String SQL_QUERY_BY_ID = SQL_QUERY_FIND_ALL + " where id = :id";
    private static final String SQL_QUERY_BY_TITLE = SQL_QUERY_FIND_ALL + " where title like :title";

    private static final String SQL_INSERT = "insert into books (title, content, created_on) values (:title, :content, :created_on)";
    private static final String SQL_UPDATE = "update books set title = :title, content = :content, updated_on = :updated_on where id = :id";
    
    private static final String SQL_DELETE = "delete from books where id = :id";

    // private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    // public JdbcBookRepository(NamedParameterJdbcTemplate jdbcTemplate){
    //     this.jdbcTemplate = jdbcTemplate;
    // }

    // @Autowired
    // BookRowMapper bookRowMapper;

    // @Override
    // public Book findById(Integer bookId){
    //     try {
    //         SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", bookId);
    //         return jdbcTemplate.queryForObject(SQL_QUERY_BY_ID, parameters, bookRowMapper);
    //     } catch (IncorrectResultSizeDataAccessException e) {
    //         throw new ResourceNotFoundException("book with ID="+bookId.toString()+" not found");
    //     }
    // }

    @Override
    public Book findById(Integer bookId) {
        String sql = """
                SELECT b.id, b.title, b.content, b.created_on, b.updated_on, r.id AS review_id, r.name, r.email, r.content AS content2, r.like_status, r.created_on AS created_on2, r.updated_on AS updated_on2
                FROM books b LEFT JOIN reviews r ON b.id = r.book_id WHERE b.id = :bookId
                """;
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("bookId", bookId);
        Book result = jdbcTemplate.query(sql, parameters, new BookMapExtractor());
        if(result == null)
            throw new ResourceNotFoundException("book with ID="+bookId.toString()+" not found");
        return result;
    }

    @Override
    public Iterable<Book> findAll(){
        return jdbcTemplate.query(SQL_QUERY_FIND_ALL, bookRowMapper);
    }

    @Override
    public Iterable<Book> searchByTitle(String title){
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("title", "%"+title+"%");
        return jdbcTemplate.query(SQL_QUERY_BY_TITLE, parameters, bookRowMapper);
    }

    @Override
    public Book addBook(Book book) {
        SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("title", book.getTitle())
		.addValue("content", book.getContent())
        .addValue("created_on", Timestamp.valueOf(book.getCreatedOn()))
        ;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        
        // jdbcTemplate.update(String sql, SqlParameterSource paramSource);
        // jdbcTemplate.update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder);
        jdbcTemplate.update(SQL_INSERT, parameters, generatedKeyHolder);

        Number key = generatedKeyHolder.getKey();
        return findById(key.intValue());
    }

    @Override
    public Book updateBook(Book book){
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("id", book.getId())
		.addValue("title", book.getTitle())
		.addValue("content", book.getContent())
        .addValue("updated_on", Timestamp.valueOf(book.getUpdatedOn()));

        jdbcTemplate.update(SQL_UPDATE, parameters);
        return book;
    }

    @Override
    public void deleteById(Integer bookId){
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", bookId);
        jdbcTemplate.update(SQL_DELETE, parameters);
    }

}

