package ru.otus.shatokhin.service.localization;

public interface LocalisationIOService {

    String readStringWithLocalePrompt(String keyMsgPrompt, Object... args);

    void outputLocaleString(String keyMsg, Object... args);
}
