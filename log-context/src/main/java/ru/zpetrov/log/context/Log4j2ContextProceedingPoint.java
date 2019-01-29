package ru.zpetrov.log.context;

import org.apache.logging.log4j.CloseableThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;
import java.util.Map;

class Log4j2ContextProceedingPoint implements ContextProceedingPoint {

    private final ProceedingJoinPoint delegate;
    private Map<String, String> context = new HashMap<>();

    Log4j2ContextProceedingPoint(ProceedingJoinPoint delegate) {
        this.delegate = delegate;
    }

    @Override
    public ContextProceedingPoint with(Map<String, String> context) {
        this.context.putAll(context);
        return this;
    }

    @Override
    public Object proceed() throws Throwable {
        try (final CloseableThreadContext.Instance ignored = CloseableThreadContext.putAll(context)) {
            return delegate.proceed();
        }
    }

}
