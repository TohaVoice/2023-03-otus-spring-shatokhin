package ru.otus.shatokhin.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.otus.shatokhin.configs.app.AppPropsImpl;
import ru.otus.shatokhin.configs.dao.CsvDaoConfig;
import ru.otus.shatokhin.configs.locale.LocaleConfigImpl;

@Configuration
@EnableConfigurationProperties({AppPropsImpl.class, CsvDaoConfig.class, LocaleConfigImpl.class})
public class ApplicationConfig {
}
