package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Author;

import java.util.List;

public interface AuthorService {

    void create(Author author);

    List<Author> getAll();

    Author getById(long id);
}
