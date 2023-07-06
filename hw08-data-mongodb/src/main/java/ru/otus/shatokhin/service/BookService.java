package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Book;

import java.util.List;

public interface BookService {

    void save(Book book);

    Book findById(String id);

    String getBookNameById(String id);

    List<Book> findAll();

    String getBooksAsString();

    String getBookByIdAsString(String id);

    List<Book> findByGenreName(String genreName);

    String findByGenreNameAsString(String genreName);

    void addGenreToBookById(String bookId, String genre);

    void deleteGenreFromBookById(String bookId, String genre);

    void deleteById(String id);
}
