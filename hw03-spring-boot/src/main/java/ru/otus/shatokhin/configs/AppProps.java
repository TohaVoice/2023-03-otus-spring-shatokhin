package ru.otus.shatokhin.configs;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public record AppProps(Locale locale, String questionsPath, int successScore) {

    public String questionsPath() {
        return getLocalisedFilePath(questionsPath);
    }

    private String getLocalisedFilePath(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        String baseName = FilenameUtils.getBaseName(fileName);
        String path = FilenameUtils.getPath(fileName);
        return path + baseName + "_" + locale + "." + extension;
    }

}
