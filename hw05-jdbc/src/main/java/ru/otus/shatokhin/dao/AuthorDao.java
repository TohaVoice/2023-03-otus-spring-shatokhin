package ru.otus.shatokhin.dao;

import ru.otus.shatokhin.domain.Author;

import java.util.List;

public interface AuthorDao {

    void create(Author author);

    List<Author> getAll();

    Author getById(long id);

}
