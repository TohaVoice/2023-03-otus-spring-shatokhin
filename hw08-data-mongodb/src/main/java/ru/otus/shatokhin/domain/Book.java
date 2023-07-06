package ru.otus.shatokhin.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "book")
public class Book {

    @Id
    @Indexed
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "releaseYear")
    private int releaseYear;

    @Field(name = "authorFullName")
    private String authorFullName;

    @Field(name = "genres")
    private Set<String> genres;

    public Book(String id) {
        this.id = id;
    }

    public Book(String id, String name, int releaseYear, Author author, Set<String> genres) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        if (author != null) {
            this.authorFullName = author.getFirstName() + " " + author.getLastName();
        }
        this.genres = genres;
    }

    public Book(String name, int releaseYear, Author author, Set<String> genres) {
        this.name = name;
        this.releaseYear = releaseYear;
        if (author != null) {
            this.authorFullName = author.getFirstName() + " " + author.getLastName();
        }
        this.genres = genres;
    }

    public Book(String id, String name, int releaseYear, Author author) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        if (author != null) {
            this.authorFullName = author.getFirstName() + " " + author.getLastName();
        }
    }
}
