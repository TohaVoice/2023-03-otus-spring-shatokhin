package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.shatokhin.dao.BookDao;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.exception.EntityNotFoundException;
import ru.otus.shatokhin.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void create(Book book) {
        bookRepository.create(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookRepository.getById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id=%s is not found", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional
    public void update(Book book) {
        bookRepository.update(book);
    }

    @Override
    @Transactional
    public void addGenreToBookById(long bookId, long genreId) {
        bookRepository.addGenreToBookById(bookId, genreId);
    }

    @Override
    @Transactional
    public void deleteGenreFromBookById(long bookId, long genreId) {
        bookRepository.deleteGenreFromBookById(bookId, genreId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
