package ru.otus.shatokhin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.repository.custom.BookRepositoryCustom;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
}
