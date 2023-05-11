package ru.otus.shatokhin.repository;

import ru.otus.shatokhin.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    void create(Genre genre);

    List<Genre> getAll();

    Optional<Genre> getById(long id);
}
