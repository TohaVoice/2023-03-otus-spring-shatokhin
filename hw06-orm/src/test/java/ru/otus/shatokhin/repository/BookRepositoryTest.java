package ru.otus.shatokhin.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.repository.jpa.BookRepositoryJpa;

import java.sql.Date;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryTest {

    private final static int EXPECTED_BOOKS_COUNT = 1;
    private static final int EXISTING_BOOK_ID = 1;

    @Autowired
    private BookRepositoryJpa bookRepository;

    @Autowired
    private TestEntityManager em;

    private Book templateBook;

    @BeforeEach
    void createTemplateBook() {
        Author author = new Author(1, "George", "Martin", Date.valueOf("1948-09-20"));
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre(1, "Fantasy"));
        genres.add(new Genre(2, "Adventure"));
        templateBook = new Book(EXISTING_BOOK_ID, "Game of Thrones", 1996, author, genres);
    }

    @Test
    void shouldCreateBook() {
        templateBook.setId(0);

        bookRepository.create(templateBook);

        Book actualBook = bookRepository.getById(2).orElseThrow();

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookRepository.getById(templateBook.getId()).orElseThrow();

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> books = bookRepository.getAll();

        assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(b -> b.getId() == 1)
                .allMatch(b -> b.getAuthor().getId() == 1)
                .allMatch(b -> b.getAuthor().getFirstName().equals("George"))
                .allMatch(b -> b.getAuthor().getLastName().equals("Martin"))
                .allMatch(b -> b.getAuthor().getBirthDate().equals(Date.valueOf("1948-09-20")))
                .allMatch(b -> b.getGenres().size() == 2);
    }

    @Test
    void shouldUpdateBook() {
        templateBook.setName("A Song of Ice and Fire: Game of Thrones");

        bookRepository.update(templateBook);
        Book actualBook = bookRepository.getById(templateBook.getId()).orElseThrow();

        assertThat(actualBook).usingRecursiveComparison().ignoringFields("author")
                .isEqualTo(templateBook);
    }

    @Test
    void shouldCorrectDeleteBookById() {
        Book book = em.find(Book.class, EXISTING_BOOK_ID);

        bookRepository.remove(book);
        book = em.find(Book.class, EXISTING_BOOK_ID);

        assertNull(book);
    }

}