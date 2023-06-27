package ru.otus.shatokhin.repository;

import ru.otus.shatokhin.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    void create(Author author);

    List<Author> getAll();

    Optional<Author> getById(long id);
}
