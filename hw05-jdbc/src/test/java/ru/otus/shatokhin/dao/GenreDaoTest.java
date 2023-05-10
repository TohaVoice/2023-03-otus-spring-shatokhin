package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Genre;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoTest {

    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    void shouldCreateGenre() {
        Genre genre = new Genre(4, "Action");

        genreDao.create(genre);
        Genre actualGenre = genreDao.getById(4);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void shouldReturnExpectedGenreById() {
        Genre genre = new Genre(1, "Fantasy");
        Genre actualGenre = genreDao.getById(1);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void shouldReturnExpectedGenresList() {
        Genre fantasy = new Genre(1, "Fantasy");
        Genre adventure = new Genre(2, "Adventure");
        Genre novel = new Genre(3, "Novel");

        List<Genre> actualGenres = genreDao.getAll();

        assertThat(actualGenres).containsExactlyInAnyOrder(fantasy, adventure, novel);
    }
}