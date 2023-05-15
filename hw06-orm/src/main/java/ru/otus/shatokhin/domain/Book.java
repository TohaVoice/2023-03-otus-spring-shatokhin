package ru.otus.shatokhin.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.shatokhin.consts.AppConst;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
@NamedEntityGraph(name = AppConst.AUTHOR_GENRES_GRAPH, attributeNodes =
        {@NamedAttributeNode("author"), @NamedAttributeNode("genres")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_year")
    private int releaseYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;

    public Book(long id, String name, int releaseYear, Author author, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.author = author;
        this.genres = genres;
    }

    public Book(String name, int releaseYear, Author author, List<Genre> genres) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.author = author;
        this.genres = genres;
    }

    public Book(long id, String name, int releaseYear, Author author) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.author = author;
    }

    public Book(long id) {
        this.id = id;
    }
}
