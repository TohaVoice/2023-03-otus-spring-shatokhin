package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Book;

import java.util.List;

public interface BookService {

    void create(Book book);

    Book getById(long id);

    List<Book> getAll();

    void update(Book book);

    void addGenreToBookById(long bookId, long genreId);

    void deleteGenreFromBookById(long bookId, long genreId);

    void deleteById(long id);
}
