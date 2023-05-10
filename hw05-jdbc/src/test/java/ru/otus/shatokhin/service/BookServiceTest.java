package ru.otus.shatokhin.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.shatokhin.dao.BookDao;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.Genre;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

    private static final int EXISTING_BOOK_ID = 1;

    @MockBean
    private BookDao bookDaoMock;

    @Autowired
    private BookService bookService;

    private Book templateBook;

    @BeforeEach
    void createTemplateBook() {
        Author author = new Author(1, "George", "Martin", Date.valueOf("1948-09-20"));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Fantasy"));
        genres.add(new Genre(2, "Adventure"));
        templateBook = new Book(EXISTING_BOOK_ID, "Game of Thrones", 1996, author, genres);
    }

    @Test
    void verifyPipelineOfCreatingBook() {
        bookService.create(any());

        verify(bookDaoMock, times(1)).create(any());
    }

    @Test
    void shouldReturnExpectedBookById() {
        when(bookDaoMock.getById(1)).thenReturn(templateBook);

        Book actualBook = bookService.getById(1);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(templateBook);
    }

    @Test
    void shouldReturnExpectedBooksList() {
        when(bookDaoMock.getAll()).thenReturn(Collections.singletonList(templateBook));

        List<Book> actualBooksList = bookService.getAll();

        assertThat(actualBooksList).containsExactlyInAnyOrder(templateBook);
        assertEquals(1, actualBooksList.size());
    }

    @Test
    void verifyPipelineOfUpdateBook() {
        bookService.update(any());

        verify(bookDaoMock, times(1)).update(any());
    }

    @Test
    void verifyPipelineOfDeleteById() {
        bookService.deleteById(anyLong());

        verify(bookDaoMock, times(1)).deleteById(anyLong());
    }

    @Test
    void verifyPipelineOfAddGenreToBook() {
        bookService.addGenreToBookById(anyLong(), anyLong());

        verify(bookDaoMock, times(1)).addGenreToBookById(anyLong(), anyLong());
    }

    @Test
    void verifyPipelineOfDeleteGenreFromById() {
        bookService.deleteGenreFromBookById(anyLong(), anyLong());

        verify(bookDaoMock, times(1)).deleteGenreFromBookById(anyLong(), anyLong());
    }
}