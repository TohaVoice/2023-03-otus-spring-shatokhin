package ru.otus.shatokhin.configs.app;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public record AppPropsImpl(int successScore) implements AppProps {

}
