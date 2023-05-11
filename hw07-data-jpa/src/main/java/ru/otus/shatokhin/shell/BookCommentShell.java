package ru.otus.shatokhin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.shatokhin.domain.Book;
import ru.otus.shatokhin.domain.BookComment;
import ru.otus.shatokhin.service.BookCommentService;
import ru.otus.shatokhin.service.BookService;
import ru.otus.shatokhin.tool.TableRender;

import java.util.Arrays;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookCommentShell {

    private final TableRender tableRender;

    private final BookService bookService;

    private final BookCommentService bookCommentService;

    @ShellMethod(value = "Add book comment", key = {"bca", "book-comment-add"})
    public String createBookComment(@ShellOption long bookId, @ShellOption String comment) {
        BookComment bookComment = new BookComment(new Book(bookId), comment);
        bookCommentService.saveCommentToBook(bookComment);
        return "Comment successfully added to the book";
    }

    @ShellMethod(value = "Get book comments", key = {"bcl", "book-comment-list"})
    public String getBookCommentList(@ShellOption long bookId) {
        Book book = bookService.findById(bookId);
        List<BookComment> comments = bookCommentService.findAllByBookId(bookId);
        return tableRender.render(
                "Comments of book '" + book.getName() + "'"
                , Arrays.asList("id", "Comment")
                , (bookComment) -> Arrays.asList(String.valueOf(bookComment.getId()), bookComment.getText())
                , comments
        );
    }

    @ShellMethod(value = "Update book", key = {"bcu", "book-comment-update"})
    public String updateComment(@ShellOption long commentId, @ShellOption String changedText) {
        BookComment comment = new BookComment(commentId, changedText);
        bookCommentService.saveCommentToBook(comment);
        return "Comment updated successfully";
    }

    @ShellMethod(value = "Delete book comment", key = {"bcd", "book-comment-delete"})
    public String deleteById(@ShellOption long commentId) {
        bookCommentService.deleteById(commentId);
        return "Comment deleted successfully";
    }
}
