package ru.otus.shatokhin.exception;

public class QuestionReadException extends RuntimeException {

    public QuestionReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
