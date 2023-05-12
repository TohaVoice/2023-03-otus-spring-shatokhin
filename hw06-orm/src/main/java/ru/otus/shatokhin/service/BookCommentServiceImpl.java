package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.exception.EntityNotFoundException;
import ru.otus.shatokhin.repository.BookCommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    @Override
    @Transactional
    public void addCommentToBook(BookComment bookComment) {
        bookCommentRepository.addCommentToBook(bookComment);
    }

    @Override
    public List<BookComment> getCommentsByBookId(long bookId) {
        return bookCommentRepository.getCommentsByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public BookComment getCommentById(long id) {
        return bookCommentRepository.getCommentById(id).orElseThrow(() ->
                new EntityNotFoundException("Comment with id=%s is not found", id));
    }

    @Override
    @Transactional
    public void deleteCommentFromBook(BookComment bookComment) {
        bookCommentRepository.deleteCommentFromBook(bookComment);
    }
}
