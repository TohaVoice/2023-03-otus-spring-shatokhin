package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.Genre;

import java.util.List;

public interface GenreService {

    void save(Genre genre);

    List<Genre> findAll();

    Genre findById(long id);
}
