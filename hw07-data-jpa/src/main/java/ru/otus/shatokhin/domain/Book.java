package ru.otus.shatokhin.domain;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.shatokhin.consts.AppConst;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
@NamedEntityGraph(name = AppConst.AUTHOR_GRAPH, attributeNodes =
        {@NamedAttributeNode("author")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_year")
    private int releaseYear;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 5)
    private Set<Genre> genres;

    public Book(long id, String name, int releaseYear, Author author, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.author = author;
        this.genres = genres;
    }

    public Book(String name, int releaseYear, Author author, Set<Genre> genres) {
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
