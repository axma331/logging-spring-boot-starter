package ru.t1.ismailov.logging.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1.ismailov.logging.aspect.LoggingAspect;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@ConditionalOnProperty(prefix = "api.logging", value = "enabled", havingValue = "true")
public class LoggingAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoggingAutoConfiguration.class);

    @Bean
    LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @PostConstruct
    public void init() {
        log.debug("LoggingConfiguration initialized.");
    }
}
