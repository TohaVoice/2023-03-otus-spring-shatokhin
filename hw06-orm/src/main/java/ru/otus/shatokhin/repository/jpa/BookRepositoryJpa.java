package ru.otus.shatokhin.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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
        Map<String, Object> properties =
                Map.of("jakarta.persistence.fetchgraph", em.getEntityGraph(AppConst.AUTHOR_GENRES_GRAPH));
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b ", Book.class);
        query.setHint("jakarta.persistence.fetchgraph", em.getEntityGraph(AppConst.AUTHOR_GENRES_GRAPH));
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void addGenreToBookById(long bookId, long genreId) {
        Query query = em.createNativeQuery("insert into book_genre (book_id, genre_id) " +
                "values (:bookId, :genreId)");
        query.setParameter("bookId", bookId);
        query.setParameter("genreId", genreId);
        query.executeUpdate();
    }

    @Override
    public void deleteGenreFromBookById(long bookId, long genreId) {
        Query query = em.createNativeQuery("delete from book_genre " +
                "where book_id = :bookId and genre_id = :genreId");
        query.setParameter("bookId", bookId);
        query.setParameter("genreId", genreId);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
