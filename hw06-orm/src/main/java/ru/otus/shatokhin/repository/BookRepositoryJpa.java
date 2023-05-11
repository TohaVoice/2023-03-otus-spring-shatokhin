package ru.otus.shatokhin.repository;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void create(Book book) {
        em.persist(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b join fetch b.genres", Book.class);
        EntityGraph<?> entityGraph = em.createEntityGraph("book-author-graph");
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void addGenreToBookById(long bookId, long genreId) {

    }

    @Override
    public void deleteGenreFromBookById(long bookId, long genreId) {

    }

    @Override
    public void deleteById(long id) {
        TypedQuery<Book> query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id", Book.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
