package ru.otus.shatokhin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shatokhin.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
