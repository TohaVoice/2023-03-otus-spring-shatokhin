package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoTest {

    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    void shouldCreateAuthor() {
        Author author = new Author(2, "Leo", "Tolstoy", Date.valueOf("1948-09-20"));

        authorDao.create(author);
        Author actualAuthor = authorDao.getById(2);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void shouldReturnExpectedAuthorById() {
        Author author = new Author(1, "George", "Martin", Date.valueOf("1948-09-20"));
        Author actualAuthor = authorDao.getById(1);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void shouldReturnExpectedAuthorsList() {
        Author author = new Author(1, "George", "Martin", Date.valueOf("1948-09-20"));

        List<Author> actualAuthors = authorDao.getAll();

        assertThat(actualAuthors).containsExactlyInAnyOrder(author);
    }
}