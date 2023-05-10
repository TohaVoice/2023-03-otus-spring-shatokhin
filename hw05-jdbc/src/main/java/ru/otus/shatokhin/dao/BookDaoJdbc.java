package ru.otus.shatokhin.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.dao.ext.BookGenreRelation;
import ru.otus.shatokhin.domain.Genre;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final GenreDao genreDao;

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
        return fleshOutBook(book);

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

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }

    private List<Book> fleshOutBooks(List<Book> books) {
        List<Genre> genres = genreDao.getAll();
        List<BookGenreRelation> bookGenreRelationRelations = getBookGenreRelations();
        mergeBooksInfo(books, genres, bookGenreRelationRelations);
        return books;
    }

    private Book fleshOutBook(Book book) {
        if (book == null) {
            return null;
        }
        List<Genre> bookGenres = findGenresByBookId(book.getId());
        book.setGenres(bookGenres);
        return book;
    }

    private List<BookGenreRelation> getBookGenreRelations() {
        return namedParameterJdbc.query("select book_id, genre_id from book_genre bg order by bg.book_id, bg.genre_id",
                (rs, i) -> new BookGenreRelation(rs.getLong(1), rs.getLong(2)));
    }

    private void mergeBooksInfo(List<Book> books, List<Genre> genres,
                                List<BookGenreRelation> bookGenreRelationRelations) {
        Map<Long, Genre> genresMap = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));
        Map<Long, Book> booksMap = books.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        bookGenreRelationRelations.forEach(r -> {
            if (booksMap.containsKey(r.getBookId()) && genresMap.containsKey(r.getGenreId())) {
                Book book = booksMap.get(r.getBookId());
                if (CollectionUtils.isEmpty(book.getGenres())) {
                    book.setGenres(new ArrayList<>());
                }
                booksMap.get(r.getBookId()).getGenres().add(genresMap.get(r.getGenreId()));
            }
        });
    }

    private List<Genre> findGenresByBookId(long bookId) {
        Map<String, Object> params = Collections.singletonMap("bookId", bookId);
        return namedParameterJdbc.query(
                "select g.id, g.name from genre g " +
                        "inner join book_genre bg ON bg.genre_id = g.id " +
                        "where bg.book_id = :bookId", params, new GenreMapper());
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
