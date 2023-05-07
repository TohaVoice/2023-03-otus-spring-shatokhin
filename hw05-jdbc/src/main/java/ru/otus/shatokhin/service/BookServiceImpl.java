package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.dao.BookDao;
import ru.otus.shatokhin.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public void create(Book book) {
        bookDao.create(book);
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void addGenreToBookById(long bookId, long genreId) {
        bookDao.addGenreToBookById(bookId, genreId);
    }

    @Override
    public void deleteGenreFromBookById(long bookId, long genreId) {
        bookDao.deleteGenreFromBookById(bookId, genreId);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
