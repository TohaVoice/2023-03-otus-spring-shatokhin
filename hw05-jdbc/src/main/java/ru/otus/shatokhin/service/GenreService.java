package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Genre;

import java.util.List;

public interface GenreService {

    void create(Genre genre);

    List<Genre> getAll();

    Genre getById(long id);
}
