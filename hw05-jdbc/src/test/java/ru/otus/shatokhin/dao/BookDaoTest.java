package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.Test;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoTest {

    private BookDao bookDao;

    @Test
    void create() {
        Author author = new Author("George", "Martin", Date.valueOf("1948-09-20"));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Fantasy"));
        genres.add(new Genre("Adventure"));
        Book book = new Book("Game of Thrones", 1996, author, genres);

        bookDao.create(book);
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}