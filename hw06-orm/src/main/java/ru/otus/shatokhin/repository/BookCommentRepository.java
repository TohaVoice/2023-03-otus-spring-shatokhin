package ru.otus.shatokhin.repository;

import ru.otus.shatokhin.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {

    void addCommentToBook(BookComment bookComment);

    List<BookComment> getCommentsByBookId(long bookId);

    Optional<BookComment> getCommentById(long id);

    void updateComment(BookComment bookComment);

    void deleteCommentById(long id);
}
