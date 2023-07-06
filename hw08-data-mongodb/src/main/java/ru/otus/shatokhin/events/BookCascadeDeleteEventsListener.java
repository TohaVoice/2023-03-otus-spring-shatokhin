package ru.otus.shatokhin.events;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.repository.BookCommentRepository;

@Component
@RequiredArgsConstructor
public class BookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final BookCommentRepository bookCommentRepository;

    @Override
    public void onAfterDelete(@NonNull AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document source = event.getSource();
        String bookId = source.get("_id").toString();
        bookCommentRepository.deleteAllByBookId(bookId);
    }
}
