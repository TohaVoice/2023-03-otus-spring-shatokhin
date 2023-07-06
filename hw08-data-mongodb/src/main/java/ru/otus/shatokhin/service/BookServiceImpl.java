package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.exception.DocumentNotFoundException;
import ru.otus.shatokhin.repository.BookRepository;
import ru.otus.shatokhin.tool.TableRender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final String BOOK_NOT_FOUND = "Book with id=%s is not found";

    private final BookRepository bookRepository;

    private final TableRender tableRender;

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new DocumentNotFoundException(BOOK_NOT_FOUND, id));
    }

    @Override
    public String getBookNameById(String id) {
        return bookRepository.getBookNameById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public String getBooksAsString() {
        var bookProjections = findAll();
        return renderBooksAsString(bookProjections);
    }

    @Override
    public String getBookByIdAsString(String id) {
        var bookProjection = findById(id);
        return renderBooksAsString(Collections.singletonList(bookProjection));
    }

    @Override
    public List<Book> findByGenreName(String genreName) {
        return bookRepository.findAllByGenreName(genreName);
    }

    @Override
    public String findByGenreNameAsString(String genreName) {
        var bookProjections = findByGenreName(genreName);
        return renderBooksAsString(bookProjections);
    }

    @Override
    public void addGenreToBookById(String bookId, String genre) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new DocumentNotFoundException(BOOK_NOT_FOUND, bookId));
        Set<String> genres = book.getGenres();
        if (genres.contains(genre)) {
            throw new RuntimeException("The genre is already listed in the book");
        }

        genres.add(genre);
        book.setGenres(genres);
        bookRepository.save(book);
    }

    @Override
    public void deleteGenreFromBookById(String bookId, String genre) {
        final Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new DocumentNotFoundException(BOOK_NOT_FOUND, bookId));
        Set<String> genres = book.getGenres();
        if (!genres.contains(genre)) {
            throw new RuntimeException("The genre is not listed in the book");
        }

        genres.remove(genre);
        book.setGenres(genres);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    private String renderBooksAsString(List<Book> bookProjections) {
        return tableRender.render(
                "The Library Books",
                Arrays.asList("id", "Name", "Author", "Release Year", "Genres"),
                (book) -> Arrays.asList(book.getId(), book.getName()
                        , book.getAuthorFullName(), String.valueOf(book.getReleaseYear())
                        , String.join(", ", book.getGenres())),
                bookProjections
        );
    }
}
