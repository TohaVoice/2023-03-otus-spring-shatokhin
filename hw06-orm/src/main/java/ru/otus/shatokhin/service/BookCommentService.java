package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentService {

    void addCommentToBook(BookComment bookComment);

    List<BookComment> getCommentsByBookId(long bookId);

    BookComment getCommentById(long id);

    void updateComment(BookComment bookComment);

    void deleteCommentById(long id);
}
