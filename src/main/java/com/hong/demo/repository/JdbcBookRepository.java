package com.hong.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

// import lombok.RequiredArgsConstructor;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
// import java.util.UUID;

import org.springframework.stereotype.Repository;

// import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.dao.IncorrectResultSizeDataAccessException;
// import org.springframework.dao.DuplicateKeyException;

import com.hong.demo.domain.Book;
import com.hong.demo.domain.Review;
import com.hong.demo.domain.Image;
import com.hong.demo.domain.LikeStatus;
import com.hong.demo.exceptions.ResourceNotFoundException;
import com.hong.demo.exceptions.DuplicateException;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;


@Repository
@AllArgsConstructor
public class JdbcBookRepository implements BookRepository { 

    private static final String SQL_QUERY_FIND_ALL = "select * from books"; 

    // private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    // public JdbcBookRepository(NamedParameterJdbcTemplate jdbcTemplate){
    //     this.jdbcTemplate = jdbcTemplate;
    // }

    // @Autowired
    // BookRowMapper bookRowMapper;

    @Override
    public boolean findById(Integer bookId){
        String sql = "SELECT * FROM books WHERE id = :bookId";

        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("bookId", bookId);
        boolean rs = jdbcTemplate.queryForRowSet(sql, parameters).first();
        if(!rs)
            throw new ResourceNotFoundException("book with Id="+bookId.toString()+" not found");
        return rs;
    }

    @Override
    public boolean findByTitle(String title){
        String sql = "SELECT * FROM books WHERE title = :title";

        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("title", title);
        return jdbcTemplate.queryForRowSet(sql, parameters).first();
    }

    @Override
    public Iterable<Book> getAllBooks(){
        return jdbcTemplate.query(SQL_QUERY_FIND_ALL, bookRowMapper);
    }

    @Override
    public Iterable<Book> searchByTitle(String title) {
        String sql = "select * from books where title LIKE :title";

        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("title", "%"+title+"%");
        return jdbcTemplate.query(sql, parameters, bookRowMapper);
    }

    @Override
    public Book getBookById(Integer bookId) {
        String sql = """
                SELECT b.id, b.title, b.content, b.created_on, b.updated_on, 
                r.id AS review_id, r.name, r.email, r.content AS content2, r.like_status, r.created_on AS created_on2, r.updated_on AS updated_on2,
                i.id AS image_id, i.uuid, i.title AS image_title
                FROM books b 
                LEFT JOIN reviews r ON b.id = r.book_id 
                LEFT JOIN images i ON b.id = i.book_id
                WHERE b.id = :bookId
            """;

        SqlParameterSource parameters = new MapSqlParameterSource().addValue("bookId", bookId);
        Book result = jdbcTemplate.query(sql, parameters, new BookMapExtractor());

        if(result == null)
            throw new ResourceNotFoundException("book with Id="+bookId.toString()+" not found");
        return result;
    }

    @Override
    public Book addBook(Book book) {
        String sql = """
                insert into books (title, content, created_on) values 
                (:title, :content, :created_on)
		    """;
        SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("title", book.getTitle())
		.addValue("content", book.getContent())
        .addValue("created_on", Timestamp.valueOf(LocalDateTime.now()))
        ;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        // try {
            jdbcTemplate.update(sql, parameters, generatedKeyHolder);
        // } catch (DuplicateKeyException e) {
        //     throw new DuplicateException("book title: " + book.getTitle() + " already exists.");
        // }
        
        Number key = generatedKeyHolder.getKey();
        return getBookById(key.intValue());
    }

    @Override
    public Book updateBook(Book book){
        String sql = """
                update books set title = :title, content = :content, updated_on = :updated_on 
                where id = :id
		    """;
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("id", book.getId())
		.addValue("title", book.getTitle())
		.addValue("content", book.getContent())
        .addValue("updated_on", Timestamp.valueOf(book.getUpdatedOn()));

        jdbcTemplate.update(sql, parameters);
        return book;
    }

    @Override
    public void deleteById(Integer bookId) {
        String sql = "delete from books where id = :id";
        
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("id", bookId);
        jdbcTemplate.update(sql, parameters);
    }

    // images

    // @Override
    public List<Image> getImagesOfBook(Integer bookId){
        String sql = "select * from images where book_id = :bookId";
        
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("bookId", bookId);
        return jdbcTemplate.query(sql, parameters, new ImageRowMapper());
    }

    public Image getImageById(Integer imageId){
        String sql = "select * from images where id = :imageId";
        
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("imageId", imageId);
        Image image = jdbcTemplate.queryForObject(sql, parameters, new ImageRowMapper());
        
        if(image == null)
            throw new ResourceNotFoundException("image with Id="+imageId.toString()+" not found");
        return image;
    }

    public Image addImage(Image image){
        String sql = "insert into images (book_id, uuid, title) values (:bookId, :uuId, :title)";
        
        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("bookId", image.getBookId())
		.addValue("uuId", UUID.randomUUID().toString())
        .addValue("title", image.getTitle());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameters, generatedKeyHolder);

        Number key = generatedKeyHolder.getKey();
        return getImageById(key.intValue());
    }

}

