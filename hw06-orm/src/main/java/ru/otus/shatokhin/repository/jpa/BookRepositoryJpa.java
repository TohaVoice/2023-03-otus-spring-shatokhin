package ru.otus.shatokhin.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.consts.AppConst;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.repository.BookRepository;

import java.util.List;
import java.util.Map;
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
        EntityGraph<?> authorGraph = em.getEntityGraph(AppConst.AUTHOR_GRAPH);
        Map<String, Object> properties =
                Map.of("jakarta.persistence.fetchgraph", authorGraph);
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        EntityGraph<?> authorGraph = em.getEntityGraph(AppConst.AUTHOR_GRAPH);
        query.setHint("jakarta.persistence.fetchgraph", authorGraph);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }


    @Override
    public void remove(Book book) {
        em.remove(book);
    }
}
