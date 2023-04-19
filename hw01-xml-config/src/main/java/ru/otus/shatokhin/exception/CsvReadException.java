package ru.otus.shatokhin.exception;

public class CsvReadException extends RuntimeException {

    public CsvReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
