package ru.otus.shatokhin.repository;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.events.BookCascadeDeleteEventsListener;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("BookRepository has to ")
@DataMongoTest
@Import(BookCascadeDeleteEventsListener.class)
class BookRepositoryTest {

    private final static int EXPECTED_BOOKS_COUNT = 1;
    private static final String EXISTING_BOOK_ID = "1";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCommentRepository bookCommentRepository;

    private Book templateBook;

    @BeforeEach
    void createTemplateBook() {
        Author author = new Author("1", "George", "Martin", Date.valueOf("1948-09-20"));
        templateBook = new Book(EXISTING_BOOK_ID, "Game of Thrones", 1996, author,
                Sets.newHashSet("Fantasy", "Adventure"));
    }

    @Test
    @DisplayName("return the expected book name")
    void shouldReturnExpectedBookNameById() {
        String bookName = bookRepository.getBookNameById(templateBook.getId());

        Assertions.assertEquals(templateBook.getName(), bookName);
    }

    @Test
    @DisplayName("return expected list of books by genre")
    void shouldReturnExpectedBooksListByGenre() {
        List<Book> book = bookRepository.findAllByGenreName("Fantasy");

        assertThat(book).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(b -> b.getId().equals(templateBook.getId()))
                .allMatch(b -> b.getName().equals(templateBook.getName()))
                .allMatch(b -> b.getAuthorFullName().equals(templateBook.getAuthorFullName()))
                .allMatch(b -> b.getReleaseYear() == templateBook.getReleaseYear())
                .allMatch(b -> b.getGenres().equals(templateBook.getGenres()));
    }

    @Test
    @DisplayName("delete the book and all related comments")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldCorrectDeleteBookAndRelatedComments() {
        bookRepository.deleteById(templateBook.getId());

        Optional<Book> book = bookRepository.findById(templateBook.getId());
        List<BookComment> bookComments = bookCommentRepository.findALlByBookId(templateBook.getId());

        Assertions.assertFalse(book.isPresent());
        assertThat(bookComments).hasSize(0);
    }

}