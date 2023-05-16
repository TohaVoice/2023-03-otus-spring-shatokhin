package ru.otus.shatokhin.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.repository.jpa.GenreRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryTest {

    @Autowired
    private GenreRepositoryJpa genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldCreateGenre() {
        Genre genre = new Genre("Action");

        genreRepository.create(genre);
        Genre actualGenre = em.find(Genre.class, 4);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void shouldReturnExpectedGenreById() {
        Genre genre = new Genre(1, "Fantasy");
        Genre actualGenre = genreRepository.getById(1).get();

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void shouldReturnExpectedGenresList() {
        Genre fantasy = new Genre(1, "Fantasy");
        Genre adventure = new Genre(2, "Adventure");
        Genre novel = new Genre(3, "Novel");

        List<Genre> actualGenres = genreRepository.getAll();

        assertThat(actualGenres).containsExactlyInAnyOrder(fantasy, adventure, novel);
    }
}