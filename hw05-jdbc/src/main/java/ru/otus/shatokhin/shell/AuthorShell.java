package ru.otus.shatokhin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.service.AuthorService;
import ru.otus.shatokhin.tool.TableRender;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

    private final AuthorService authorService;

    private final TableRender tableRender;

    @ShellMethod(value = "Get author list", key = {"al", "author-list"})
    public String authorList() {
        List<Author> authors = authorService.getAll();
        return tableRender.render(
                "Authors"
                , Arrays.asList("id", "First Name", "Last Name", "Birth Date")
                , (author) -> Arrays.asList(String.valueOf(author.getId())
                        , author.getFirstName()
                        , author.getLastName()
                        , author.getBirthDate().toString()
                )
                , authors
        );
    }

    @ShellMethod(value = "Create new author", key = {"ca", "author-create"})
    public String createAuthor(@ShellOption String firstName, @ShellOption String lastName
            , @ShellOption String birthDate) {
        authorService.create(new Author(firstName, lastName, Date.valueOf(birthDate)));
        return "Author successfully added to the library";
    }

    @ShellMethod(value = "Get author", key = {"ag", "author-get"})
    public String getAuthorById(@ShellOption long id) {
        return tableRender.singleRowRender(
                "Author"
                , Arrays.asList("id", "First Name", "Last Name", "Birth Date")
                , (author) -> Arrays.asList(String.valueOf(author.getId())
                        , author.getFirstName()
                        , author.getLastName()
                        , author.getBirthDate().toString())
                , authorService.getById(id)
        );
    }
}
