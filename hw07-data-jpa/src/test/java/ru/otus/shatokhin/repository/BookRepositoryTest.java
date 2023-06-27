package ru.otus.shatokhin.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.dto.BookProjection;

import java.sql.Date;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class BookRepositoryTest {

    private final static int EXPECTED_BOOKS_COUNT = 1;
    private static final int EXISTING_BOOK_ID = 1;

    @Autowired
    private BookRepository bookRepository;

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
    void shouldReturnExpectedBookById() {
        BookProjection actualBookProjection =
                bookRepository.findWithInterfaceProjectionWithoutQueryById(templateBook.getId()).orElseThrow();

        assertThat(actualBookProjection).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldReturnExpectedBookNameById() {
        String bookName = bookRepository.getBookNameById(templateBook.getId());

        Assertions.assertEquals(templateBook.getName(), bookName);
    }

    @Test
    void shouldReturnExpectedBooksList() {
        List<BookProjection> bookProjections = bookRepository.findAllWithInterfaceProjectionWithoutQueryBy();

        assertThat(bookProjections).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(b -> b.getId().equals("1"))
                .allMatch(b -> b.getName().equals("Game of Thrones"))
                .allMatch(b -> b.getAuthorFullName().equals("George Martin"))
                .allMatch(b -> b.getReleaseYear().equals("1996"))
                .allMatch(b -> b.getGenreNames().equals("Fantasy, Adventure"));
    }

    @Test
    void shouldReturnExpectedBooksListByGenre() {
        List<BookProjection> bookProjections = bookRepository.findByGenreNameWithInterfaceProjection("Fantasy");

        assertThat(bookProjections).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(b -> b.getId().equals("1"))
                .allMatch(b -> b.getName().equals("Game of Thrones"))
                .allMatch(b -> b.getAuthorFullName().equals("George Martin"))
                .allMatch(b -> b.getReleaseYear().equals("1996"))
                .allMatch(b -> b.getGenreNames().equals("Fantasy, Adventure"));
    }

}