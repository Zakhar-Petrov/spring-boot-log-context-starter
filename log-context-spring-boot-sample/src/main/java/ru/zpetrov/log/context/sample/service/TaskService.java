package ru.zpetrov.log.context.sample.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.zpetrov.log.context.annotations.ContextItem;
import ru.zpetrov.log.context.annotations.Log4j2Context;

import java.time.OffsetDateTime;

@Service
@Log4j2
public class TaskService {

    private final LoggingService loggingService;

    public TaskService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Log4j2Context
    public void doTask(
            @ContextItem("id") long taskId,
            @ContextItem(value = "timestamp", expression = "#this.toInstant().toEpochMilli()") OffsetDateTime taskDateTime,
            @ContextItem("task") String task) {
        loggingService.log("Task " + taskId + " '" + task + "' is executing");
        log.info("test");
    }

}
