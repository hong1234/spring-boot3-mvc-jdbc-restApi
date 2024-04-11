package com.hong.demo.repository;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper; 


import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Date;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hong.demo.domain.Book;

@Component
public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setContent(rs.getString("content"));
        book.setCreatedOn(convertToLocalDateTime(rs.getTimestamp("created_on")));
        book.setUpdatedOn(convertToLocalDateTime(rs.getTimestamp("updated_on")));
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


