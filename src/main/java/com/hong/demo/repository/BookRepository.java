package com.hong.demo.repository;

// import java.util.Set;
// import java.util.Optional;
import com.hong.demo.domain.Book;
import com.hong.demo.domain.Image;
import java.util.*;

public interface BookRepository {

    boolean findById(Integer bookId);
    boolean findByTitle(String title);

    Iterable<Book> getAllBooks();
    Iterable<Book> searchByTitle(String title);

    Book getBookById(Integer id);
    Book addBook(Book book);
    Book updateBook(Book book);

    void deleteById(Integer id);

    Iterable<Image> getImagesOfBook(Integer bookId);

    Image addImage(Image book);
}
