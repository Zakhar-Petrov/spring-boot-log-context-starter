package ru.zpetrov.log.context;

import ru.zpetrov.log.context.annotations.Log4j2Context;

public class Log4j2ContextWrapper extends AbstractLogContextWrapper {
    public Log4j2ContextWrapper() {
        super(Log4j2Context.class);
    }
}
