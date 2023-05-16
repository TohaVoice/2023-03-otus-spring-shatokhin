package ru.otus.shatokhin.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void create(Genre genre) {
        em.persist(genre);
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }
}
