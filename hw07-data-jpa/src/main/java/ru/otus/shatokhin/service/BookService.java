package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.dto.BookProjection;

import java.util.List;

public interface BookService {

    void save(Book book);

    BookProjection findById(long id);

    String getBookNameById(long id);

    List<BookProjection> findAll();

    String getBooksAsString();

    String getBookByIdAsString(long id);

    List<BookProjection> findByGenreName(String genreName);

    String findByGenreNameAsString(String genreName);

    void addGenreToBookById(long bookId, long genreId);

    void deleteGenreFromBookById(long bookId, long genreId);

    void deleteById(long id);
}
