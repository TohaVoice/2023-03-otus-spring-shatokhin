package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Book;

import java.util.List;

public interface BookService {

    void save(Book book);

    Book findById(long id);

    List<Book> findAll();

    List<Book> findByGenreName(String genreName);

    void addGenreToBookById(long bookId, long genreId);

    void deleteGenreFromBookById(long bookId, long genreId);

    void deleteById(long id);
}
