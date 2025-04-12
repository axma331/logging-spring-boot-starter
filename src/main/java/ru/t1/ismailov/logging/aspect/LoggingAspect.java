package ru.t1.ismailov.logging.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(ru.t1.ismailov.logging.annotation.Loggable)")
    public void loggable() {
    }

    @Before("loggable()")
    public void logBefore(JoinPoint jp) {
        Object[] args = jp.getArgs();

        log.info("Processing {} with args: {}", getClassAndMethodName(jp), Arrays.toString(args));
    }

    private String getClassAndMethodName(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        return "%s.%s".formatted(
                signature.getDeclaringType().getSimpleName(),
                signature.getName()
        );
    }
}
