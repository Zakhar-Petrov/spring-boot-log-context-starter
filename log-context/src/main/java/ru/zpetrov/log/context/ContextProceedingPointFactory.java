package ru.zpetrov.log.context;

import ru.zpetrov.log.context.annotations.Log4j2Context;

import java.lang.annotation.Annotation;
import java.util.Optional;

class ContextProceedingPointFactory {

    Optional<ContextProceedingPointCreator> getCreatorByAnnotation(Class<? extends Annotation> annotationClass) {
        if (annotationClass.isAssignableFrom(Log4j2Context.class)) {
            return Optional.of(Log4j2ContextProceedingPoint::new);
        }
        return Optional.empty();
    }
}
