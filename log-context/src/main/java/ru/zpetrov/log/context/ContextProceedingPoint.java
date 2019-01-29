package ru.zpetrov.log.context;

import java.util.Map;

interface ContextProceedingPoint {

    ContextProceedingPoint with(Map<String, String> context);

    Object proceed() throws Throwable;

}
