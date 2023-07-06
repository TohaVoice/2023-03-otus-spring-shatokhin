package ru.otus.shatokhin.service;

import ru.otus.shatokhin.domain.BookComment;

import java.util.List;

public interface BookCommentService {

    void saveCommentToBook(BookComment bookComment);

    List<BookComment> findAllByBookId(String bookId);

    BookComment findById(String id);

    void deleteById(String id);
}
