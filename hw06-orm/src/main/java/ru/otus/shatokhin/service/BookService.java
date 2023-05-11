package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.dto.BookDTO;

import java.util.List;

public interface BookService {

    void create(Book book);

    BookDTO getById(long id);

    String getBookNameById(long id);

    List<BookDTO> getAll();

    void update(Book book);

    void addGenreToBookById(long bookId, long genreId);

    void deleteGenreFromBookById(long bookId, long genreId);

    void deleteById(long id);
}
