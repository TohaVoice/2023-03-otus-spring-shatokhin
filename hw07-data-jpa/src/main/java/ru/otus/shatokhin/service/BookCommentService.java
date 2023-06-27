package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.BookComment;

import java.util.List;

public interface BookCommentService {

    void saveCommentToBook(BookComment bookComment);

    List<BookComment> findAllByBookId(long bookId);

    BookComment findById(long id);

    void deleteById(long id);
}
