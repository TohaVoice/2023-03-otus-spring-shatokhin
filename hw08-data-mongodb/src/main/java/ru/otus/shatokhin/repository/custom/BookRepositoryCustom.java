package ru.otus.shatokhin.repository.custom;

import ru.otus.shatokhin.domain.Book;

import java.util.List;

public interface BookRepositoryCustom {

    String getBookNameById(String id);

    List<Book> findAllByGenreName(String genreName);
}
