package ru.t1.ismailov.logging.init;

import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import ru.t1.ismailov.logging.exeption.LoggingStartupException;

import java.io.IOException;
import java.util.List;

public class LoggingEnvPostProcessor implements EnvironmentPostProcessor {

    private final Log log;
    private final YamlPropertySourceLoader propertySourceLoader;

    public LoggingEnvPostProcessor(DeferredLogFactory logFactory) {
        this.log = logFactory.getLog(LoggingEnvPostProcessor.class);
        this.propertySourceLoader = new YamlPropertySourceLoader();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("Initializing LoggingEnvironmentPostProcessor");

        loadDefaultYmlProperty(environment);
        validateEnabledProperty(environment);
        validateLogLevelProperty(environment);
    }

    private void loadDefaultYmlProperty(ConfigurableEnvironment env) {
        String fileName = "default.yml";
        var resource = new ClassPathResource(fileName);

        if (resource == null || !resource.exists()) {
            log.warn("Default property file " + fileName + " not found.");
            return;
        }
        try {
            List<PropertySource<?>> sources = propertySourceLoader.load("api.logging-starter", resource);

            if (sources == null || sources.isEmpty()) {
                log.warn("No property sources loaded from " + fileName);
                return;
            }
            sources.forEach(env.getPropertySources()::addLast);
        } catch (IOException e) {
            throw new LoggingStartupException("Failed to load default logging properties.", e);
        }
    }

    private void validateEnabledProperty(ConfigurableEnvironment env) {
        String enabledPropertyValue = env.getProperty("api.logging-starter.enabled");

        if (enabledPropertyValue != null) {
            switch (enabledPropertyValue.toLowerCase()) {
                case "true", "false" ->
                        log.info("Property value 'api.logging-starter.enabled' is valid. Enabled: " + enabledPropertyValue);
                default -> throw new LoggingStartupException(
                        "Invalid property value for 'api.logging-starter.enabled': " + enabledPropertyValue
                );
            }
        } else {
            log.info("Property value 'api.logging-starter.enabled' is not set in the environment. Using default values.");
        }
    }

    private void validateLogLevelProperty(ConfigurableEnvironment env) {
        String levelPropertyValue = env.getProperty("api.logging-starter.level");

        if (levelPropertyValue != null) {
            switch (levelPropertyValue.toLowerCase()) {
                case "trace", "debug", "info", "warn", "error" -> {
                    log.info("Property value 'api.logging-starter.level' is valid. Level: " + levelPropertyValue);
                }
                default -> throw new LoggingStartupException(
                        "Invalid property value 'api.logging-starter.level'. Level: " + levelPropertyValue
                );
            }
        } else {
            log.info("Property value 'api.logging-starter.level' is not set in the environment. Using default values.");
        }
    }
}
