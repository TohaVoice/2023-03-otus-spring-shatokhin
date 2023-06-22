package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.exception.EntityNotFoundException;
import ru.otus.shatokhin.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final String BOOK_NOT_FOUND = "Book with id=%s is not found";

    private final BookRepository bookRepository;

    private final GenreService genreService;

    @Override
    @Transactional
    public void create(Book book) {
        bookRepository.create(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookRepository.getById(id).orElseThrow(() ->
                new EntityNotFoundException(BOOK_NOT_FOUND, id));
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
        Book book = bookRepository.getById(bookId).orElseThrow(() ->
                new EntityNotFoundException(BOOK_NOT_FOUND, bookId));
        Genre genre = genreService.getById(genreId);

        book.getGenres().add(genre);
        bookRepository.update(book);
    }

    @Override
    @Transactional
    public void deleteGenreFromBookById(long bookId, long genreId) {
        final Book book = bookRepository.getById(bookId).orElseThrow(() ->
                new EntityNotFoundException(BOOK_NOT_FOUND, bookId));

        Genre genre = book.getGenres().stream()
                .filter(g -> genreId == g.getId())
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("The book with id = %s doesn't have genre with id = %s"
                                , bookId, genreId));

        book.getGenres().remove(genre);
        bookRepository.update(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Book book = bookRepository.getById(id).orElseThrow(() ->
                new EntityNotFoundException(BOOK_NOT_FOUND, id));

        bookRepository.remove(book);
    }
}
