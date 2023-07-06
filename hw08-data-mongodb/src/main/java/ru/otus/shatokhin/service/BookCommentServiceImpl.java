package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.exception.DocumentNotFoundException;
import ru.otus.shatokhin.repository.BookCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private static final String COMMENT_NOT_FOUND = "Comment with id=%s is not found";

    private final BookCommentRepository bookCommentRepository;

    @Override
    public void saveCommentToBook(BookComment bookComment) {
        bookCommentRepository.save(bookComment);
    }

    @Override
    public List<BookComment> findAllByBookId(String bookId) {
        return bookCommentRepository.findALlByBookId(bookId);
    }

    @Override
    public BookComment findById(String id) {
        return bookCommentRepository.findById(id).orElseThrow(() ->
                new DocumentNotFoundException(COMMENT_NOT_FOUND, id));
    }

    @Override
    public void deleteById(String id) {
        bookCommentRepository.deleteById(id);
    }
}
