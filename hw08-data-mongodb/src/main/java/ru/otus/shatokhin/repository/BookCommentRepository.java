package ru.otus.shatokhin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.shatokhin.domain.BookComment;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {

    List<BookComment> findALlByBookId(String bookId);

    void deleteAllByBookId(String bookId);
}
