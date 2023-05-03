package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Book;

import java.util.List;

public interface BookService {

    void create(Book book);

    Book getById();

    List<Book> getAll();

    void update(Book book);

    void deleteById(long id);
}
