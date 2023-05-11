package ru.otus.shatokhin.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.BookComment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookCommentRepositoryTest {

    @Autowired
    private BookCommentRepository bookCommentRepository;

    @Autowired
    private TestEntityManager em;

    private Book templateBook;

    @BeforeEach
    void init() {
        templateBook = em.find(Book.class, 1);
    }

    @Test
    void shouldReturnExpectedCommentsFromBook() {
        BookComment bookComment1 = new BookComment(templateBook, "An excellent book!");
        bookComment1.setId(1);
        BookComment bookComment2 = new BookComment(templateBook, "The storyline is too long((");
        bookComment2.setId(2);

        List<BookComment> comments = bookCommentRepository.findAllByBookId(1);

        assertThat(comments).containsExactlyInAnyOrder(bookComment1, bookComment2);
    }
}
