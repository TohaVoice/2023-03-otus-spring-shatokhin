package ru.otus.shatokhin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.service.BookService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService bookService;

    @ShellMethod(value = "Get book list", key = {"bl", "book-list"})
    public String bookList() {
        return bookService.getBooksAsString();
    }

    @ShellMethod(value = "Get book list by genre name", key = {"blg", "book-list-by-genre"})
    public String bookListByGenreName(@ShellOption String genreName) {
        return bookService.findByGenreNameAsString(genreName);
    }

    @ShellMethod(value = "Create book", key = {"bc", "book-create"})
    public String createBook(@ShellOption String name, @ShellOption long authorId, @ShellOption int releaseYear
            , @ShellOption String genreIds) {
        Set<Genre> genres = convertIdsToGenres(genreIds);
        Book book = new Book(name, releaseYear, new Author(authorId), genres);
        bookService.save(book);

        return "Book successfully added to the library";
    }

    @ShellMethod(value = "Get book", key = {"bg", "book-get"})
    public String getById(@ShellOption long id) {
        return bookService.getBookByIdAsString(id);
    }

    @ShellMethod(value = "Delete book", key = {"bd", "book-delete"})
    public String deleteById(@ShellOption long id) {
        bookService.deleteById(id);
        return "Book deleted successfully";
    }

    @ShellMethod(value = "Update book", key = {"bu", "book-update"})
    public String updateById(@ShellOption long id, @ShellOption String name, @ShellOption long authorId
            , @ShellOption int releaseYear, @ShellOption String genreIds) {
        Set<Genre> genres = convertIdsToGenres(genreIds);
        Book book = new Book(id, name, releaseYear, new Author(authorId), genres);
        bookService.save(book);
        return "Book updated successfully";
    }

    @ShellMethod(value = "Add genre to book", key = {"bag", "book-add-genre"})
    public String addGenreToBook(@ShellOption long bookId, @ShellOption long genreId) {
        bookService.addGenreToBookById(bookId, genreId);
        return "Genre added successfully";
    }

    @ShellMethod(value = "Delete genre to book", key = {"bdg", "book-delete-genre"})
    public String deleteGenreFromBook(@ShellOption long bookId, @ShellOption long genreId) {
        bookService.deleteGenreFromBookById(bookId, genreId);
        return "Genre deleted successfully";
    }

    private Set<Genre> convertIdsToGenres(String genreIds) {
        return Stream.of(genreIds.split(","))
                .map(id -> new Genre(Long.parseLong(id)))
                .collect(Collectors.toSet());
    }

}
