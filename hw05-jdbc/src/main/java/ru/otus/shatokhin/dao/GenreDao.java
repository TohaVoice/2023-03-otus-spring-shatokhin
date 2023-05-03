package ru.otus.shatokhin.dao;

import ru.otus.shatokhin.domain.Genre;

import java.util.List;

public interface GenreDao {

    void create(Genre genre);

    List<Genre> getAll();

    Genre getById();
}
