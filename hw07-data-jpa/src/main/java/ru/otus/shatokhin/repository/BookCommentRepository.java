package ru.otus.shatokhin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.shatokhin.domain.BookComment;

import java.util.List;

public interface BookCommentRepository extends CrudRepository<BookComment, Long> {

    List<BookComment> findAllByBookId(long bookId);
}
