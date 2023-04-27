package ru.otus.shatokhin.service.localization;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.configs.locale.LocaleConfig;

@Service
@AllArgsConstructor
public class LocalisationMessageServiceImpl implements LocalisationMessageService {

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localeConfig.locale());
    }
}
