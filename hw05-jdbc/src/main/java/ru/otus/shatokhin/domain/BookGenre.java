package ru.otus.shatokhin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookGenre {
    
    private long bookId;
    
    private Genre genre;
}
