package ru.otus.shatokhin.service.localization;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.configs.AppProps;

@Service
@AllArgsConstructor
public class LocalisationMessageServiceImpl implements LocalisationMessageService {

    private final MessageSource messageSource;

    private final AppProps props;

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, props.locale());
    }
}
