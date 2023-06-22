package ru.otus.shatokhin.repository;

import ru.otus.shatokhin.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void create(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void update(Book book);

    void addGenreToBookById(long bookId, long genreId);

    void deleteGenreFromBookById(long bookId, long genreId);

    void deleteById(long id);
}
