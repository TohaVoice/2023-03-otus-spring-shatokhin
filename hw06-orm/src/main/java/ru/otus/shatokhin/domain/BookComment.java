package ru.otus.shatokhin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book_comment")
public class BookComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", updatable = false)
    @ToString.Exclude
    private Book book;

    public BookComment(Book book, String text) {
        this.text = text;
        this.book = book;
    }

    public BookComment(long id, String text) {
        this.id = id;
        this.text = text;
    }
}
