package ru.otus.shatokhin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shatokhin.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
