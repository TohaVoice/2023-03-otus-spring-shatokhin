package ru.otus.shatokhin.repository;

import ru.otus.shatokhin.domain.BookComment;

import java.util.List;

public interface BookCommentRepository {

    void addCommentToBook(long bookId, BookComment bookComment);

    List<BookComment> getCommentsByBookId(long bookId);

    void deleteCommentFromBook(long bookId);
}
