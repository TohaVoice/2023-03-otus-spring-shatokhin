package ru.otus.shatokhin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.domain.BookComment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void addCommentToBook(long bookId, BookComment bookComment) {
        em.createQuery("insert ", BookComment.class);
    }

    @Override
    public List<BookComment> getCommentsByBookId(long bookId) {
        return null;
    }

    @Override
    public void deleteCommentFromBook(long bookId) {

    }
}
