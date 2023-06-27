package ru.otus.shatokhin.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.shatokhin.consts.AppConst;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.dto.BookProjection;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b.name from Book b where b.id = :id")
    String getBookNameById(@Param("id") long id);

    @EntityGraph(AppConst.AUTHOR_GRAPH)
    List<BookProjection> findAllWithInterfaceProjectionWithoutQueryBy();

    @EntityGraph(AppConst.AUTHOR_GRAPH)
    Optional<BookProjection> findWithInterfaceProjectionWithoutQueryById(long id);

    @EntityGraph(AppConst.AUTHOR_GRAPH)
    @Query("select b from Book b join b.genres g where g.name = :genre")
    List<BookProjection> findByGenreNameWithInterfaceProjection(@Param("genre") String genreName);
}
