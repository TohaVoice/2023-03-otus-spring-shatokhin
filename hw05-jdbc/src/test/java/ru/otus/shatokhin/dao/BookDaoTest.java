package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BookDaoTest {

    private static final int EXISTING_BOOK_ID = 1;

    private BookDao bookDao;

    private Book templateBook;

    @BeforeEach
    void createTemplateBook() {
        Author author = new Author("George", "Martin", Date.valueOf("1948-09-20"));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Fantasy"));
        genres.add(new Genre("Adventure"));
        templateBook = new Book(EXISTING_BOOK_ID, "Game of Thrones", 1996, author, genres);
    }

    @Test
    void shouldCreateBook() {
        templateBook.setId(2);

        Book createdBook = bookDao.create(templateBook);
        Book actualBook = bookDao.getById(createdBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(createdBook);
    }

    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookDao.getById(templateBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> actualBooksList = bookDao.getAll();
        assertThat(actualBooksList)
                .containsExactlyInAnyOrder(templateBook);
    }

    @Test
    void shouldUpdateBook() {
        templateBook.setName("A Song of Ice and Fire: Game of Thrones");

        Book updatedBook = bookDao.update(templateBook);
        Book actualBook = bookDao.getById(updatedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}