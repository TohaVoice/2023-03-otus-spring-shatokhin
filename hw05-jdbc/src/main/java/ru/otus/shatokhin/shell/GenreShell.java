package ru.otus.shatokhin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.service.GenreService;
import ru.otus.shatokhin.tool.TableRender;

import java.util.Arrays;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {

    private final GenreService genreService;

    private final TableRender tableRender;

    @ShellMethod(value = "Get genre list", key = {"gl", "genre-list"})
    public String genreList() {
        List<Genre> genres = genreService.getAll();
        return tableRender.render(
                "Genres"
                , Arrays.asList("id", "Name")
                , (genre) -> Arrays.asList(String.valueOf(genre.getId()), genre.getName())
                , genres
        );
    }

    @ShellMethod(value = "Create genre", key = {"gc", "genre-create"})
    public String createGenre(@ShellOption String name) {
        genreService.create(new Genre(name));
        return "Genre successfully added to the library";
    }

    @ShellMethod(value = "Get genre", key = {"gg", "genre-get"})
    public String getGenreById(@ShellOption long id) {
        return tableRender.singleRowRender(
                "Genre"
                , Arrays.asList("id", "Name")
                , (genre) -> Arrays.asList(String.valueOf(genre.getId()), genre.getName())
                , genreService.getById(id)
        );
    }
}