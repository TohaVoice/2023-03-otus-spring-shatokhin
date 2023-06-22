package ru.otus.shatokhin.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.repository.jpa.BookCommentRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Import(BookCommentRepositoryJpa.class)
public class BookCommentRepositoryTest {

    @Autowired
    private BookCommentRepositoryJpa bookCommentRepository;

    @Autowired
    private TestEntityManager em;

    private Book templateBook;

    @BeforeEach
    void init() {
        templateBook = em.find(Book.class, 1);
    }

    @Test
    void shouldAddCommentToBook() {
        BookComment bookComment = new BookComment(templateBook, "Test comment");

        bookCommentRepository.addCommentToBook(bookComment);
        BookComment actualBookComment = em.find(BookComment.class, 3);

        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(bookComment);
    }

    @Test
    void shouldReturnExpectedCommentById() {
        BookComment bookComment = new BookComment(templateBook, "An excellent book!");
        bookComment.setId(1);

        BookComment actualBookComment = bookCommentRepository.getCommentById(1).orElseThrow();

        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(bookComment);
    }

    @Test
    void shouldReturnExpectedCommentsFromBook() {
        BookComment bookComment1 = new BookComment(templateBook, "An excellent book!");
        bookComment1.setId(1);
        BookComment bookComment2 = new BookComment(templateBook, "The storyline is too long((");
        bookComment2.setId(2);

        List<BookComment> comments = bookCommentRepository.getCommentsByBookId(1);

        assertThat(comments).containsExactlyInAnyOrder(bookComment1, bookComment2);
    }

    @Test
    void shouldUpdateComment() {
        BookComment bookComment = new BookComment(templateBook, "New Comment");
        bookComment.setId(1);

        bookCommentRepository.updateComment(bookComment);
        BookComment actualComment = em.find(BookComment.class, 1);

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(bookComment);
    }

    @Test
    void shouldCorrectDeleteCommentById() {
        BookComment bookComment = em.find(BookComment.class, 1);

        bookCommentRepository.removeComment(bookComment);
        bookComment = em.find(BookComment.class, 1);

        assertNull(bookComment);
    }
}
