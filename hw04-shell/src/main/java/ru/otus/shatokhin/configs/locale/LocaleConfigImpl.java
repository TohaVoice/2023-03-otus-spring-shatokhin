package ru.otus.shatokhin.configs.locale;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public record LocaleConfigImpl(Locale locale) implements LocaleConfig {

}
