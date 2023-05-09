package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@Import(BookDaoJdbc.class)
class BookDaoTest {

    private static final int EXISTING_BOOK_ID = 1;

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private BookDaoJdbc bookDao;

    private Book templateBook;

    @BeforeEach
    void createTemplateBook() {
        Author author = new Author(1, "George", "Martin", Date.valueOf("1948-09-20"));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Fantasy"));
        genres.add(new Genre(2, "Adventure"));
        templateBook = new Book(EXISTING_BOOK_ID, "Game of Thrones", 1996, author, genres);
    }

    @Test
    void shouldCreateBook() {
        templateBook.setId(2);

        bookDao.create(templateBook);
        Book actualBook = bookDao.getById(2);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookDao.getById(templateBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldReturnExpectedBooksList() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Fantasy"));
        genres.add(new Genre(2, "Adventure"));
        genres.add(new Genre(3, "Novel"));
        when(genreDao.getAll()).thenReturn(genres);

        List<Book> actualBooksList = bookDao.getAll();

        assertThat(actualBooksList).containsExactlyInAnyOrder(templateBook);
    }

    @Test
    void shouldUpdateBook() {
        templateBook.setName("A Song of Ice and Fire: Game of Thrones");

        bookDao.update(templateBook);
        Book actualBook = bookDao.getById(templateBook.getId());

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

    @Test
    void shouldAddGenreToBook() {
        templateBook.getGenres().add(new Genre(3, "Novel"));

        bookDao.addGenreToBookById(EXISTING_BOOK_ID, 3);
        Book actualBook = bookDao.getById(templateBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldCorrectDeleteGenreFromBook() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();
        templateBook.getGenres().remove(1);

        bookDao.deleteGenreFromBookById(EXISTING_BOOK_ID, 2);
        Book actualBook = bookDao.getById(templateBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }
}