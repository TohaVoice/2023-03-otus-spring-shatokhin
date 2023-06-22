package ru.otus.shatokhin.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book_comment")
public class BookComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookComment(Book book, String text) {
        this.text = text;
        this.book = book;
    }

    public BookComment(long id, Book book) {
        this.id = id;
        this.book = book;
    }

    public BookComment(long id) {
        this.id = id;
    }
}
