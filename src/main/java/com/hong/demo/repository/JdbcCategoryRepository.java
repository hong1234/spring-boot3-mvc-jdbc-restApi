package com.hong.demo.repository;

import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.hong.demo.domain.Book;
import com.hong.demo.domain.Category;
import com.hong.demo.domain.CategoryBook;
import com.hong.demo.exceptions.ResourceNotFoundException;

@Repository
@AllArgsConstructor
public class JdbcCategoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public boolean findById(Integer categoryId){
        String sql = "SELECT * FROM categories WHERE id = :categoryId";

        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("categoryId", categoryId);
        boolean rs = jdbcTemplate.queryForRowSet(sql, parameters).first();
        if(!rs)
            throw new ResourceNotFoundException("category with Id="+categoryId.toString()+" not found");
        return rs;
    }

    public Iterable<Category> getAllCategories(){
        String sql = """
                SELECT c.id AS c_id, c.name, b.id AS b_id, b.title, b.content, b.created_on
                FROM categories c 
                LEFT JOIN category_book cb ON c.id = cb.category_id
                LEFT JOIN books b ON cb.book_id = b.id
                """;

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Category>>() {
            @Override
            public List<Category> extractData(ResultSet rs) throws SQLException, DataAccessException { 
                Category category;
                HashMap<String, Category> categoryMap = new HashMap<>();
                List<Book> books;
                
                while(rs.next()) {
                    if(categoryMap.containsKey(rs.getString("c_id"))) {
                        category = categoryMap.get(rs.getString("c_id"));
                        books = category.getBooks();
                    } else {
                        category = new Category();
                        category.setId(rs.getInt("c_id"));
                        category.setName(rs.getString("name"));
                        books = new ArrayList<>();
                    }

                    if(rs.getString("b_id") != null){
                        Book book = new Book();// , );
                        book.setId(rs.getInt("b_id"));
                        book.setTitle(rs.getString("title"));
                        book.setContent(rs.getString("content"));
                        book.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
                        books.add(book);
                    }

                    category.setBooks(books);
                    categoryMap.put(rs.getString("c_id"), category);
                }

                return new ArrayList<>(categoryMap.values());
            }
        });
        
    }

    public Category getCategoryById(Integer categoryId){
        String sql = """
                SELECT c.id AS c_id, c.name, b.id AS b_id, b.title, b.content, b.created_on
                FROM categories c 
                LEFT JOIN category_book cb ON c.id = cb.category_id
                LEFT JOIN books b ON cb.book_id = b.id
                WHERE c.id = :categoryId
                """;

        SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("categoryId", categoryId);
        
        return jdbcTemplate.query(sql, parameters, new ResultSetExtractor<Category>() {
            @Override
            public Category extractData(ResultSet rs) throws SQLException, DataAccessException {
                Category category = null;
                Map<String, Book> bookMap = new HashMap<>();

                while(rs.next()) {
                    if (category == null) {
                        category = new Category();
                        category.setId(rs.getInt("c_id"));
                        category.setName(rs.getString("name"));
                    }

                    if (rs.getString("b_id") != null) {
                        Book book = new Book();
                        book.setId(rs.getInt("b_id"));
                        book.setTitle(rs.getString("title"));
                        book.setContent(rs.getString("content"));
                        book.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
                        bookMap.put(Integer.toString(book.getId()), book);
                    }
                }

                if(category != null) {
                    List<Book> books = new ArrayList<>(bookMap.values());
                    category.setBooks(books);
                }

                return category;
            }
        });
        
    }

    public void addCategoryBook(CategoryBook cabo) {
        String sql = "insert into category_book (category_id, book_id) values (:categoryId, :bookId)";

        SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("categoryId", cabo.getCategoryId())
		.addValue("bookId", cabo.getBookId());

        jdbcTemplate.update(sql, parameters);                              
    }

}
