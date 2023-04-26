package ru.otus.shatokhin.service.localization;

import lombok.AllArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.service.IOService;

@Service
@AllArgsConstructor
public class LocalisationIOServiceImpl implements LocalisationIOService {

    private final IOService ioService;

    private final LocalisationMessageService msgService;

    @Override
    public String readStringWithLocalePrompt(String keyMsgPrompt, Object... args) {
        String message = getBaseMessageIfKeyNotFound(keyMsgPrompt, args);
        return ioService.readStringWithPrompt(message);
    }

    @Override
    public void outputLocaleString(String keyMsg, Object... args) {
        String message = getBaseMessageIfKeyNotFound(keyMsg, args);
        ioService.outputString(message);
    }

    private String getBaseMessageIfKeyNotFound(String keyMsg, Object... args) {
        try {
            return msgService.getMessage(keyMsg, args);
        } catch (NoSuchMessageException e) {
            return keyMsg;
        }
    }
}
