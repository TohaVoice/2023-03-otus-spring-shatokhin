package ru.otus.shatokhin.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.google.common.collect.Sets;
import com.mongodb.client.MongoDatabase;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.repository.AuthorRepository;
import ru.otus.shatokhin.repository.BookCommentRepository;
import ru.otus.shatokhin.repository.BookRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@ChangeLog(order = "001")
public class TestInitMongoDBDataChangeLog {

    private Author martin;

    private Author dostoevsky;

    private Book gameOfThrones;

    private Book theIdiot;

    private Book theBrothersKaramazov;

    @ChangeSet(order = "000", id = "dropDB", author = "ashatokhin", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "ashatokhin", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        martin = repository.save(new Author("George", "Martin",
                Date.from(LocalDate.of(1948, 9, 20)
                        .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
        dostoevsky = repository.save(new Author("Fedor", "Dostoevsky",
                Date.from(LocalDate.of(1821, 11, 11)
                        .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "ashatokhin", runAlways = true)
    public void initBooks(BookRepository repository) {
        gameOfThrones = repository.save(new Book("1", "Game of Thrones", 1996, martin,
                Sets.newHashSet("Fantasy", "Adventure")));
        theIdiot = repository.save(new Book("2", "The Idiot", 1868, dostoevsky,
                Sets.newHashSet("Novel")));
        theBrothersKaramazov = repository.save(new Book("3", "The Brothers Karamazov", 1879, dostoevsky,
                Sets.newHashSet("Novel")));
    }

    @ChangeSet(order = "003", id = "initComments", author = "ashatokhin", runAlways = true)
    public void initComments(BookCommentRepository repository) {
        repository.save(new BookComment("An excellent book!", gameOfThrones));
        repository.save(new BookComment("The storyline is too long((", gameOfThrones));
        repository.save(new BookComment("It''s cool", theIdiot));
        repository.save(new BookComment("It was a pleasure to read this book", theIdiot));
        repository.save(new BookComment("After reading it, I recommended it to all my friend"
                , theBrothersKaramazov));
    }
}
