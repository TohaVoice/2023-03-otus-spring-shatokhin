package ru.otus.shatokhin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book_comment")
public class BookComment {

    @Id
    @Indexed
    private String id;

    @Field(name = "text")
    private String text;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @DBRef(lazy = true)
    @Field(name = "book")
    private Book book;

    public BookComment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public BookComment(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
