package ru.otus.shatokhin.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.shatokhin.consts.AppConst;
import ru.otus.shatokhin.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(AppConst.AUTHOR_GRAPH)
    List<Book> findAll();

    @EntityGraph(AppConst.AUTHOR_GRAPH)
    @Query("select b from Book b join b.genres g where g.name = :genre")
    List<Book> findByGenreName(@Param("genre") String genreName);
}
