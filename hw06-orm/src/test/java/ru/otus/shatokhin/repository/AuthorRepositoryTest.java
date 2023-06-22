package ru.otus.shatokhin.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.repository.jpa.AuthorRepositoryJpa;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepositoryJpa authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldCreateAuthor() {
        Author author = new Author("Leo", "Tolstoy", Date.valueOf("1948-09-20"));

        authorRepository.create(author);
        Author actualAuthor = em.find(Author.class, 2);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void shouldReturnExpectedAuthorById() {
        Author author = new Author(1, "George", "Martin", Date.valueOf("1948-09-20"));
        Author actualAuthor = authorRepository.getById(1).get();

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void shouldReturnExpectedAuthorsList() {
        Author author = new Author(1, "George", "Martin", Date.valueOf("1948-09-20"));

        List<Author> actualAuthors = authorRepository.getAll();

        assertThat(actualAuthors).containsExactlyInAnyOrder(author);
    }
}