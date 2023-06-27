package ru.otus.shatokhin.dto;

import org.springframework.beans.factory.annotation.Value;
import ru.otus.shatokhin.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

public interface BookProjection {

    String getId();

    String getName();

    String getReleaseYear();

    @Value("#{target.author.firstName + ' ' + target.author.lastName}")
    String getAuthorFullName();

    @Value("#{T(ru.otus.shatokhin.dto.BookProjection).genresAsString(target.genres)}")
    String getGenreNames();

    static String genresAsString(List<Genre> actors) {
        return actors.stream().map(Genre::getName).collect(Collectors.joining(", "));
    }
}
