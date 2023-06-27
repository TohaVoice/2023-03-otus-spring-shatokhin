package ru.otus.shatokhin.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
