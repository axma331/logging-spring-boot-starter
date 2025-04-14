package ru.t1.ismailov.logging.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1.ismailov.logging.aspect.LoggingAspect;
import ru.t1.ismailov.logging.config.properties.LoggingProperties;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "api.logging-starter", value = "enabled", havingValue = "true")
public class LoggingAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoggingAutoConfiguration.class);

    private final LoggingProperties properties;

    @Autowired
    public LoggingAutoConfiguration(LoggingProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        log.info("LoggingConfiguration initialized.");
    }


    @Bean
    @ConditionalOnMissingBean
    public LoggingAspect loggingAspect() {
        log.info("LoggingAspect bean initialized.");
        return new LoggingAspect(properties);
    }
}
