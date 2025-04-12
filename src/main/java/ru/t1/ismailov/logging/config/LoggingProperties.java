package ru.t1.ismailov.logging.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.logging")
public record LoggingProperties(boolean enabled, String logLevel) {

    private static final Logger log = LoggerFactory.getLogger(LoggingProperties.class);

    public LoggingProperties {
        if (logLevel == null || logLevel.isBlank()) {
            logLevel = "info";
        }
        log.debug("LoggingProperties initialized. Enabled: {}, Level: {}", enabled, logLevel);
    }
}
