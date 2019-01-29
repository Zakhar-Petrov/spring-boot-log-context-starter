package ru.zpetrov.log.context;

import org.aspectj.lang.ProceedingJoinPoint;

public interface LogContextWrapper {

    Object setContextForMethod(ProceedingJoinPoint joinPoint) throws Throwable;

}
