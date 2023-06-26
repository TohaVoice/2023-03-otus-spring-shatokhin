package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.dto.BookProjection;
import ru.otus.shatokhin.exception.EntityNotFoundException;
import ru.otus.shatokhin.repository.BookRepository;
import ru.otus.shatokhin.tool.TableRender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final String BOOK_NOT_FOUND = "Book with id=%s is not found";

    private final BookRepository bookRepository;

    private final GenreService genreService;

    private final TableRender tableRender;

    @Override
    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookProjection findById(long id) {
        return bookRepository.findWithInterfaceProjectionWithoutQueryById(id).orElseThrow(() ->
                new EntityNotFoundException(BOOK_NOT_FOUND, id));
    }

    @Override
    public String getBookNameById(long id) {
        return bookRepository.getBookNameById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookProjection> findAll() {
        return bookRepository.findAllWithInterfaceProjectionWithoutQueryBy();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public String getBooksAsString() {
        var bookProjections = findAll();
        return renderBooksAsString(bookProjections);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public String getBookByIdAsString(long id) {
        var bookProjection = findById(id);
        return renderBooksAsString(Collections.singletonList(bookProjection));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookProjection> findByGenreName(String genreName) {
        return bookRepository.findByGenreNameWithInterfaceProjection(genreName);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public String findByGenreNameAsString(String genreName) {
        var bookProjections = findByGenreName(genreName);
        return renderBooksAsString(bookProjections);
    }

    @Override
    @Transactional
    public void addGenreToBookById(long bookId, long genreId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException(BOOK_NOT_FOUND, bookId));
        Genre genre = genreService.findById(genreId);

        book.getGenres().add(genre);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteGenreFromBookById(long bookId, long genreId) {
        final Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException(BOOK_NOT_FOUND, bookId));

        Genre genre = book.getGenres().stream()
                .filter(g -> genreId == g.getId())
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("The book with id = %s doesn't have genre with id = %s"
                                , bookId, genreId));

        book.getGenres().remove(genre);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private String renderBooksAsString(List<BookProjection> bookProjections) {
        return tableRender.render(
                "The Library Books",
                Arrays.asList("id", "Name", "Author", "Release Year", "Genres"),
                (book) -> Arrays.asList(book.getId(), book.getName()
                        , book.getAuthorFullName(), book.getReleaseYear()
                        , book.getGenreNames()),
                bookProjections
        );
    }
}
