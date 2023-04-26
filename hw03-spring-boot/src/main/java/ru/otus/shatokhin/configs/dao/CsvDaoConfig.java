package ru.otus.shatokhin.configs.dao;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public record CsvDaoConfig(String fileName, Locale locale) implements DaoConfig {

    @Override
    public String fileName() {
        return getLocalisedFilePath(fileName);
    }

    private String getLocalisedFilePath(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        String baseName = FilenameUtils.getBaseName(fileName);
        String path = FilenameUtils.getPath(fileName);
        return path + baseName + "_" + locale + "." + extension;
    }
}
