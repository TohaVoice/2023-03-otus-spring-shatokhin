package ru.otus.shatokhin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String id;

    private String name;

    private String releaseYear;

    private String authorFullName;

    private String genres;

    public BookDTO(Book book) {
        this(String.valueOf(book.getId())
                , book.getName()
                , String.valueOf(book.getReleaseYear())
                , authorToString(book.getAuthor())
                , genresToString(book.getGenres()));
    }

    private static String authorToString(Author author) {
        return author.getFirstName() + " " + author.getLastName();
    }

    private static String genresToString(Set<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(","));
    }
}
