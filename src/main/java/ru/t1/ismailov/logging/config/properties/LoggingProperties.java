package ru.t1.ismailov.logging.config.properties;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.logging-starter")
public record LoggingProperties(Boolean enabled, String level) {

    private static final Logger log = LoggerFactory.getLogger(LoggingProperties.class);

    @PostConstruct
    private void init() {
        log.info("LoggingProperties initialized. Enabled: {}, Level: {}", enabled, level);
    }
}
