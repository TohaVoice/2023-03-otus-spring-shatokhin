package ru.otus.shatokhin.domain;

import lombok.Data;

import java.util.List;

@Data
public class Book {

    private long id;

    private String name;

    private int releaseYear;

    private Author author;

    private List<Genre> genres;

    public Book(String name, int releaseYear, Author author, List<Genre> genres) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.author = author;
        this.genres = genres;
    }
}
