package ru.otus.shatokhin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.domain.BookComment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void addCommentToBook(BookComment bookComment) {
        em.persist(bookComment);
    }

    @Override
    public List<BookComment> getCommentsByBookId(long bookId) {
        TypedQuery<BookComment> query = em.createQuery(
                "select bc from BookComment bc where bc.book.id = :bookId", BookComment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<BookComment> getCommentById(long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public void updateComment(BookComment bookComment) {
        em.merge(bookComment);
    }

    @Override
    public void deleteCommentById(long id) {
        Query query = em.createQuery("delete " +
                "from BookComment bc " +
                "where bc.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
