package com.hong.demo.repository;

import java.util.Set;
import java.util.Optional;
import com.hong.demo.domain.Book;

public interface BookRepository {
    Book findById(Integer id);
    Iterable<Book> searchByTitle(String title);
    Iterable<Book> findAll();

    Book addBook(Book book);

    Book updateBook(Book book);

    void deleteById(Integer id);
}
