package ru.otus.shatokhin.exception;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
