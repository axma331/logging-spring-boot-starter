package ru.t1.ismailov.logging.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for API logging.
 * <p>
 * These properties allow enabling or disabling logging and setting the desired log level.
 *
 * <p>Default values:
 * <ul>
 *   <li><b>enabled</b> — {@code false}</li>
 *   <li><b>level</b> — {@code INFO}</li>
 * </ul>
 */
@ConfigurationProperties(prefix = "api.logging-starter")
public record LoggingProperties(boolean enabled, String level) {

    public LoggingProperties {
        level = level == null ? "INFO" : level;
    }
}
