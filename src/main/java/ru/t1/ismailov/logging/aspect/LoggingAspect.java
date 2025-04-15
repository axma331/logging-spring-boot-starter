package ru.t1.ismailov.logging.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.t1.ismailov.logging.config.properties.LoggingProperties;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    private final LoggingProperties properties;

    @Autowired
    public LoggingAspect(LoggingProperties properties) {
        this.properties = properties;
    }


    @Before("@annotation(ru.t1.ismailov.logging.annotation.Loggable)" +
            " || @within(ru.t1.ismailov.logging.annotation.Loggable)")
    public void logBefore(JoinPoint jp) {
        logMessage("Processing " + getClassAndMethodName(jp) + " with args: " + Arrays.toString(jp.getArgs()));
    }


    @AfterReturning(pointcut = "@annotation(ru.t1.ismailov.logging.annotation.Loggable)", returning = "result")
    public void logAfterReturningServiceMethod(JoinPoint jp, Object result) {
        logMessage("Method " + getClassAndMethodName(jp) + " executed successfully with result: " + result);
    }

    @Around("@annotation(ru.t1.ismailov.logging.annotation.MeasureExecutionTime)")
    public Object logMeasureExecutionTime(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String classAndMethodName = getClassAndMethodName(jp);
        Object result;
        try {
            result = jp.proceed();
        } catch (Exception ex) {
            log.error("Failure to measure execution time. Error in {}.", classAndMethodName, ex);
            throw ex;
        }
        long executionTime = System.currentTimeMillis() - startTime;
        logMessage("Method " + classAndMethodName + " executed after " + executionTime + " ms.");

        return result;
    }


    private void logMessage(String message) {
        switch (properties.level().toUpperCase()) {
            case "DEBUG" -> log.debug(message);
            case "WARN" -> log.warn(message);
            case "ERROR" -> log.error(message);
            default -> log.info(message);
        }
    }

    private String getClassAndMethodName(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        return signature.getDeclaringType().getSimpleName() + '.' + signature.getName();
    }
}
