package ru.zpetrov.log.context.autoconfigure;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ru.zpetrov.log.context.LogContextWrapper;

@Aspect
public class Log4j2ContextInitializer {

    private final LogContextWrapper logContextWrapper;

    public Log4j2ContextInitializer(LogContextWrapper logContextWrapper) {
        this.logContextWrapper = logContextWrapper;
    }

    @Around("@annotation(ru.zpetrov.log.context.annotations.Log4j2Context)()")
    public Object methodsAnnoatatedWithLog4j2Context(ProceedingJoinPoint joinPoint) throws Throwable {
        return logContextWrapper.setContextForMethod(joinPoint);
    }

}
