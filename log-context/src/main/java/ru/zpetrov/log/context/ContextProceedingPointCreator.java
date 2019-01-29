package ru.zpetrov.log.context;

import org.aspectj.lang.ProceedingJoinPoint;

interface ContextProceedingPointCreator {

    ContextProceedingPoint of(ProceedingJoinPoint delegate);

}
