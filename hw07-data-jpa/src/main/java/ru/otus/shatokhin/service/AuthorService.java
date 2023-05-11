package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Author;

import java.util.List;

public interface AuthorService {

    void save(Author author);

    List<Author> findAll();

    Author findById(long id);
}
