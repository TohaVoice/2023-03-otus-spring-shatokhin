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
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService bookService;

    private final TableRender tableRender;

    @ShellMethod(value = "Get book list", key = {"bl", "book-list"})
    public void bookList() {
        List<Book> books = bookService.getAll();
        System.out.println(
                tableRender.render(
                        "The Library Books",
                        Arrays.asList("id", "Name", "Author", "Release Year", "Genres"),
                        (book) -> Arrays.asList(String.valueOf(book.getId()), book.getName()
                                , authorToString(book.getAuthor()), String.valueOf(book.getReleaseYear())
                                , genresToString(book.getGenres())),
                        books
                )
        );
    }

    @ShellMethod(value = "Create book", key = {"bc", "book-create"})
    public void createBook(@ShellOption String name, @ShellOption long authorId, @ShellOption int releaseYear
            , @ShellOption String genreIds) {
        List<String> genresIds = List.of(genreIds.split(","));
        List<Genre> genres = new ArrayList<>();
        for (String genreId : genresIds) {
            genres.add(new Genre(Long.parseLong(genreId)));
        }
        Book book = new Book(name, releaseYear, new Author(authorId), genres);
        bookService.create(book);

        System.out.println("Book successfully added to the library");
    }

    @ShellMethod(value = "Get book", key = {"bg", "book-get"})
    public void getById(@ShellOption long id) {
        System.out.println(
                tableRender.singleRowRender(
                        "Book",
                        Arrays.asList("id", "Name", "Author", "Release Year", "Genres"),
                        (book) -> Arrays.asList(String.valueOf(book.getId()), book.getName()
                                , authorToString(book.getAuthor()), String.valueOf(book.getReleaseYear())
                                , genresToString(book.getGenres())),
                        bookService.getById(id)
                )
        );
    }

    @ShellMethod(value = "Delete book", key = {"bd", "book-delete"})
    public void deleteById(@ShellOption long id) {
        bookService.deleteById(id);
        System.out.println("Book deleted successfully ");
    }

    @ShellMethod(value = "Update book", key = {"bu", "book-update"})
    public void updateById(@ShellOption long id, @ShellOption String name, @ShellOption long authorId
            ,  @ShellOption int releaseYear) {
        Book book = new Book(id, name, releaseYear, new Author(authorId), null);
        bookService.update(book);
        System.out.println("Book updated successfully ");
    }

    @ShellMethod(value = "Add genre to book", key = {"bag", "book-add-genre"})
    public void addGenreToBook(@ShellOption long bookId, @ShellOption long genreId) {
        bookService.addGenreToBookById(bookId, genreId);
        System.out.println("Genre added successfully");
    }

    @ShellMethod(value = "Delete genre to book", key = {"bdg", "book-delete-genre"})
    public void deleteGenreFromBook(@ShellOption long bookId, @ShellOption long genreId) {
        bookService.deleteGenreFromBookById(bookId, genreId);
        System.out.println("Genre deleted successfully");
    }

    private String genresToString(List<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(","));
    }

    private String authorToString(Author author) {
        return author.getFirstName() + " " + author.getLastName();
    }

}
