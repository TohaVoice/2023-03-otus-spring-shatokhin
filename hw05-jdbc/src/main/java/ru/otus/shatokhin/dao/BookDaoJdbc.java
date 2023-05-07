package ru.otus.shatokhin.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.BookGenre;
import ru.otus.shatokhin.domain.Genre;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;

    @Override
    public void create(Book book) {
        SqlParameterSource parameterSource = new MapSqlParameterSource(
                Map.of("name", book.getName()
                        , "release_year", book.getReleaseYear()
                        , "author_id", book.getAuthor().getId()
                ));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbc.update("insert into book (name, release_year, author_id) " +
                "values (:name, :release_year, :author_id)", parameterSource, keyHolder);
        long bookId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        namedParameterJdbc.batchUpdate("insert into book_genre (book_id, genre_id) " +
                "values(:book_id, :genre_id)", prepareBookGenreParams(bookId, book.getGenres()));
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book = namedParameterJdbc.queryForObject(
                "select b.id as bookId, b.name as bookName, b.release_year, b.author_id, " +
                        "a.first_name, a.last_name, a.birth_date " +
                        "from book b " +
                        "inner join author a on a.id = b.author_id " +
                        "where b.id = :id", params, new BookMapper()
        );
        return fleshOutBooks(Collections.singletonList(book)).get(0);

    }

    @Override
    public List<Book> getAll() {
        List<Book> books = namedParameterJdbc.query(
                "select b.id as bookId, b.name as bookName, b.release_year, b.author_id, " +
                        "a.first_name, a.last_name, a.birth_date " +
                        "from book b " +
                        "inner join author a on a.id = b.author_id", new BookMapper()
        );
        return fleshOutBooks(books);
    }

    @Override
    public void update(Book book) {
        namedParameterJdbc.update(
                "update book set name = :name, author_id = :author_id, release_year = :release_year where id = :id",
                Map.of(
                        "id", book.getId(),
                        "author_id", book.getAuthor().getId(),
                        "release_year", book.getReleaseYear(),
                        "name", book.getName()
                )
        );
    }

    @Override
    public void addGenreToBookById(long bookId, long genreId) {
        namedParameterJdbc.update("insert into book_genre (book_id, genre_id) values (:book_id, :genre_id)"
                , Map.of("book_id", bookId
                        , "genre_id", genreId)
        );
    }

    @Override
    public void deleteGenreFromBookById(long bookId, long genreId) {
        namedParameterJdbc.update("delete from book_genre where book_id = :book_id and genre_id = :genre_id"
                , Map.of("book_id", bookId
                        , "genre_id", genreId)
        );
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("bookId");
            String bookName = resultSet.getString("bookName");
            int releaseYear = resultSet.getInt("release_year");
            long authorId = resultSet.getLong("author_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Date birthDate = resultSet.getDate("birth_date");
            Author author = new Author(authorId, firstName, lastName, birthDate);
            return new Book(bookId, bookName, releaseYear, author);
        }
    }

    private static class BookGenreMapper implements RowMapper<BookGenre> {

        @Override
        public BookGenre mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("book_id");
            long genreId = resultSet.getLong("genreId");
            String genreName = resultSet.getString("genreName");
            return new BookGenre(bookId, new Genre(genreId, genreName));
        }
    }

    private List<Book> fleshOutBooks(List<Book> books) {
        List<BookGenre> bookGenres = findBookGenreByBooks(books);
        for (Book book : books) {
            List<Genre> genres = bookGenres.stream()
                    .filter(bookGenre -> bookGenre.getBookId() == book.getId())
                    .map(BookGenre::getGenre)
                    .collect(Collectors.toList());

            book.setGenres(genres);
        }
        return books;
    }

    private List<BookGenre> findBookGenreByBooks(List<Book> books) {
        List<Long> bookIds = books.stream()
                .map(Book::getId)
                .toList();
        Map<String, Object> params = Collections.singletonMap("bookIds", bookIds);
        return namedParameterJdbc.query(
                "select g.id as genreId, g.name as genreName, bg.book_id from genre g " +
                        "inner join book_genre bg ON bg.genre_id = g.id " +
                        "where bg.book_id in (:bookIds)", params, new BookGenreMapper());
    }

    private SqlParameterSource[] prepareBookGenreParams(long bookId, List<Genre> genres) {
        List<SqlParameterSource> params = new ArrayList<>();
        for (Genre genre : genres) {
            params.add(new MapSqlParameterSource(
                    Map.of("book_id", bookId, "genre_id", genre.getId()))
            );
        }
        return params.stream().toArray(SqlParameterSource[]::new);
    }

}
