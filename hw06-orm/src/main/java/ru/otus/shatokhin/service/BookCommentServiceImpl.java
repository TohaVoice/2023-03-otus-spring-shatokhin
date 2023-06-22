package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.exception.EntityNotFoundException;
import ru.otus.shatokhin.repository.BookCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final static String COMMENT_NOT_FOUND = "Comment with id=%s is not found";

    private final BookCommentRepository bookCommentRepository;

    @Override
    @Transactional
    public void addCommentToBook(BookComment bookComment) {
        bookCommentRepository.addCommentToBook(bookComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getCommentsByBookId(long bookId) {
        return bookCommentRepository.getCommentsByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public BookComment getCommentById(long id) {
        return bookCommentRepository.getCommentById(id).orElseThrow(() ->
                new EntityNotFoundException(COMMENT_NOT_FOUND, id));
    }

    @Override
    @Transactional
    public void updateComment(BookComment bookComment) {
        bookCommentRepository.updateComment(bookComment);
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        BookComment bookComment = bookCommentRepository.getCommentById(id).orElseThrow(() ->
                new EntityNotFoundException(COMMENT_NOT_FOUND, id));

        bookCommentRepository.removeComment(bookComment);
    }
}
