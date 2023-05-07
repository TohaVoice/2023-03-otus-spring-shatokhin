package ru.otus.shatokhin.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.domain.Author;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void create(Author author) {
        namedParameterJdbcOperations.update("insert into author (first_name, last_name, birth_date) " +
                        "values (:first_name, :last_name, :birth_date)",
                Map.of("first_name", author.getFirstName()
                        , "last_name", author.getLastName()
                        , "birth_date", author.getBirthDate()
                ));
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, first_name, last_name, birth_date from author", new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, first_name, last_name, birth_date from author where id = :id", params, new AuthorMapper()
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Date birthDate = resultSet.getDate("birth_date");
            return new Author(id, firstName, lastName, birthDate);
        }
    }
}
