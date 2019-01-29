package ru.zpetrov.log.context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.Map;

abstract class AbstractLogContextWrapper implements LogContextWrapper {

    private final ContextProceedingPointFactory contextProceedingPointFactory = new ContextProceedingPointFactory();
    private final Class<? extends Annotation> annotationClass;

    public AbstractLogContextWrapper(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public Object setContextForMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Map<String, String> contextMap = new ContextBuilder(signature.getMethod(), joinPoint.getArgs()).build();
        return proceedWithContext(joinPoint, contextMap);
    }

    private Object proceedWithContext(ProceedingJoinPoint joinPoint, Map<String, String> context) throws Throwable {
        return contextProceedingPointFactory.getCreatorByAnnotation(annotationClass)
                .orElseThrow(() -> new RuntimeException("Unknown annotation for ContextProceedingPoint"))
                .of(joinPoint)
                .with(context)
                .proceed();
    }

}
