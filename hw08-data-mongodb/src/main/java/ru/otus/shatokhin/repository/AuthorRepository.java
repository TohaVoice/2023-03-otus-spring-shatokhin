package ru.otus.shatokhin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.shatokhin.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
