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
    public void saveCommentToBook(BookComment bookComment) {
        bookCommentRepository.save(bookComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> findAllByBookId(long bookId) {
        return bookCommentRepository.findAllByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public BookComment findById(long id) {
        return bookCommentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(COMMENT_NOT_FOUND, id));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }
}
