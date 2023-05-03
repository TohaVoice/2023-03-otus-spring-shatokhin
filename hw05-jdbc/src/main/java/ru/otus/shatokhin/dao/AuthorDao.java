package ru.otus.shatokhin.dao;

import ru.otus.shatokhin.domain.Author;

import java.util.List;

public interface AuthorDao {

    void create(Author book);

    List<Author> getAll();

    Author getById();

}
