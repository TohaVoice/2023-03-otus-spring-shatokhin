package ru.otus.shatokhin.repository.custom;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import ru.otus.shatokhin.domain.Book;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Data
    private static class NameProjection {

        private String name;
    }

    @Override
    public String getBookNameById(String id) {
        final Aggregation aggregation = Aggregation.newAggregation(
                match(where("id").is(id)),
                project().andExclude("_id", "releaseYear", "author", "genres"));

        NameProjection nameProjection =
                mongoTemplate.aggregate(aggregation, Book.class, NameProjection.class).getUniqueMappedResult();
        return Objects.requireNonNull(nameProjection).getName();
    }

    @Override
    public List<Book> findAllByGenreName(String genreName) {
        Aggregation aggregation = newAggregation(
                match(where("genres").all(genreName)));
        return mongoTemplate.aggregate(aggregation, Book.class, Book.class).getMappedResults();
    }
}
