package ru.t1.ismailov.logging.analyzer;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import ru.t1.ismailov.logging.exeption.LoggingStartupException;

public class LoggingFailureAnalyzer extends AbstractFailureAnalyzer<LoggingStartupException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, LoggingStartupException cause) {
        return new FailureAnalysis(cause.getMessage(), "Indicate valid values for property", cause);
    }
}