package ru.zpetrov.log.context.sample.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.zpetrov.log.context.annotations.ContextItem;
import ru.zpetrov.log.context.annotations.Log4j2Context;

@Service
@Log4j2
public class LoggingService {

    @Log4j2Context
    public void log(@ContextItem(value = "length", expression = "#this.length()") String message) {
        log.info(message);
    }

}
