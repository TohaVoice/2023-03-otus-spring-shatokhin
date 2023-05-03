package ru.otus.shatokhin.dao;

import ru.otus.shatokhin.domain.Book;

import java.util.List;

public interface BookDao {

    Book create(Book book);

    Book getById();

    List<Book> getAll();

    Book update(Book book);

    void deleteById(long id);

}
