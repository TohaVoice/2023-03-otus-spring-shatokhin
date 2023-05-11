package ru.otus.shatokhin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.service.BookService;
import ru.otus.shatokhin.tool.TableRender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService bookService;

    private final TableRender tableRender;

    @ShellMethod(value = "Get book list", key = {"bl", "book-list"})
    public String bookList() {
        List<Book> books = bookService.getAll();
        return tableRender.render(
                "The Library Books",
                Arrays.asList("id", "Name", "Author", "Release Year", "Genres"),
                (book) -> Arrays.asList(String.valueOf(book.getId()), book.getName()
                        , authorToString(book.getAuthor()), String.valueOf(book.getReleaseYear())
                        , genresToString(book.getGenres())),
                books
        );
    }

    @ShellMethod(value = "Create book", key = {"bc", "book-create"})
    public String createBook(@ShellOption String name, @ShellOption long authorId, @ShellOption int releaseYear
            , @ShellOption String genreIds) {
        List<String> genresIds = List.of(genreIds.split(","));
        List<Genre> genres = new ArrayList<>();
        for (String genreId : genresIds) {
            genres.add(new Genre(Long.parseLong(genreId)));
        }
        Book book = new Book(name, releaseYear, new Author(authorId), genres);
        bookService.create(book);

        return "Book successfully added to the library";
    }

    @ShellMethod(value = "Get book", key = {"bg", "book-get"})
    public String getById(@ShellOption long id) {
        return tableRender.singleRowRender(
                "Book",
                Arrays.asList("id", "Name", "Author", "Release Year", "Genres"),
                (book) -> Arrays.asList(String.valueOf(book.getId()), book.getName()
                        , authorToString(book.getAuthor()), String.valueOf(book.getReleaseYear())
                        , genresToString(book.getGenres())),
                bookService.getById(id)
        );
    }

    @ShellMethod(value = "Delete book", key = {"bd", "book-delete"})
    public String deleteById(@ShellOption long id) {
        bookService.deleteById(id);
        return "Book deleted successfully";
    }

    @ShellMethod(value = "Update book", key = {"bu", "book-update"})
    public String updateById(@ShellOption long id, @ShellOption String name, @ShellOption long authorId
            , @ShellOption int releaseYear) {
        Book book = new Book(id, name, releaseYear, new Author(authorId), null);
        bookService.update(book);
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

    private String genresToString(Set<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(","));
    }

    private String authorToString(Author author) {
        return author.getFirstName() + " " + author.getLastName();
    }

}
