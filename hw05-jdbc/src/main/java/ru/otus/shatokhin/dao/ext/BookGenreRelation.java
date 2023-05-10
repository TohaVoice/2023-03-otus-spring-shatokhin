package ru.otus.shatokhin.dao.ext;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookGenreRelation {
    
    private long bookId;
    
    private long genreId;
}
