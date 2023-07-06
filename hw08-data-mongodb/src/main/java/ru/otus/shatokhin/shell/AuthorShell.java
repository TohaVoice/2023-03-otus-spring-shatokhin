package ru.otus.shatokhin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.service.AuthorService;
import ru.otus.shatokhin.tool.TableRender;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final AuthorService authorService;

    private final TableRender tableRender;

    @ShellMethod(value = "Get author list", key = {"al", "author-list"})
    public String authorList() {
        List<Author> authors = authorService.findAll();
        return tableRender.render(
                "Authors"
                , Arrays.asList("id", "First Name", "Last Name", "Birth Date")
                , (author) -> Arrays.asList(String.valueOf(author.getId())
                        , author.getFirstName()
                        , author.getLastName()
                        , DATE_FORMAT.format(author.getBirthDate())
                )
                , authors
        );
    }

    @ShellMethod(value = "Create new author", key = {"ac", "author-create"})
    public String createAuthor(@ShellOption String firstName, @ShellOption String lastName
            , @ShellOption String birthDate) {

        authorService.save(new Author(firstName, lastName, fromStringToDate(birthDate)));
        return "Author successfully added to the library";
    }

    @ShellMethod(value = "Get author", key = {"ag", "author-get"})
    public String getAuthorById(@ShellOption String id) {
        return tableRender.singleRowRender(
                "Author"
                , Arrays.asList("id", "First Name", "Last Name", "Birth Date")
                , (author) -> Arrays.asList(String.valueOf(author.getId())
                        , author.getFirstName()
                        , author.getLastName()
                        , DATE_FORMAT.format(author.getBirthDate()))
                , authorService.findById(id)
        );
    }

    private Date fromStringToDate(String dateValue) {
        String[] dateSplit = dateValue.split("-");
        String year = dateSplit[0];
        String month = dateSplit[1];
        String day = dateSplit[2];
        return Date.from(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day))
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
