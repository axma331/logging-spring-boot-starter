package ru.t1.ismailov.logging.exeption;

public class LoggingStartupException extends RuntimeException {

    public LoggingStartupException(String msg) {
        super(msg);
    }

    public LoggingStartupException(String s, Throwable e) {
        super(s, e);
    }
}
