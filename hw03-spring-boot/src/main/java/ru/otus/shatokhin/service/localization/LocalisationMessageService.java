package ru.otus.shatokhin.service.localization;

public interface LocalisationMessageService {

    String getMessage(String key, Object... args);
}
