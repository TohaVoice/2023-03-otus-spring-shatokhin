package ru.otus.shatokhin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.service.AuthorService;
import ru.otus.shatokhin.service.BookService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService bookService;

    private final AuthorService authorService;

    @ShellMethod(value = "Get book list", key = {"bl", "book-list"})
    public String bookList() {
        return bookService.getBooksAsString();
    }

    @ShellMethod(value = "Get book list by genre name", key = {"blg", "book-list-by-genre"})
    public String bookListByGenreName(@ShellOption String genreName) {
        return bookService.findByGenreNameAsString(genreName);
    }

    @ShellMethod(value = "Create book", key = {"bc", "book-create"})
    public String createBook(@ShellOption String name, @ShellOption String authorId, @ShellOption int releaseYear
            , @ShellOption String genres) {
        Set<String> genresSet = splitNamesToSet(genres);
        Author author = authorService.findById(authorId);
        Book book = new Book(name, releaseYear, author, genresSet);
        bookService.save(book);

        return "Book successfully added to the library";
    }

    @ShellMethod(value = "Get book", key = {"bg", "book-get"})
    public String getById(@ShellOption String id) {
        return bookService.getBookByIdAsString(id);
    }

    @ShellMethod(value = "Delete book", key = {"bd", "book-delete"})
    public String deleteById(@ShellOption String id) {
        bookService.deleteById(id);
        return "Book deleted successfully";
    }

    @ShellMethod(value = "Update book", key = {"bu", "book-update"})
    public String updateById(@ShellOption String id, @ShellOption String name, @ShellOption String authorId
            , @ShellOption int releaseYear, @ShellOption String genres) {
        Set<String> genresSet = splitNamesToSet(genres);
        Author author = authorService.findById(authorId);
        Book book = new Book(id, name, releaseYear, author, genresSet);
        bookService.save(book);
        return "Book updated successfully";
    }

    @ShellMethod(value = "Add genre to book", key = {"bag", "book-add-genre"})
    public String addGenreToBook(@ShellOption String bookId, @ShellOption String genre) {
        bookService.addGenreToBookById(bookId, genre);
        return "Genre added successfully";
    }

    @ShellMethod(value = "Delete genre to book", key = {"bdg", "book-delete-genre"})
    public String deleteGenreFromBook(@ShellOption String bookId, @ShellOption String genre) {
        bookService.deleteGenreFromBookById(bookId, genre);
        return "Genre deleted successfully";
    }

    private Set<String> splitNamesToSet(String genreNames) {
        return Stream.of(genreNames.split(","))
                .collect(Collectors.toSet());
    }

}
