package ru.zpetrov.log.context.sample.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Log4j2
public class SchedulerService {

    private final TaskService taskService;
    private long id;

    public SchedulerService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduledTask() {
        taskService.doTask(id++, OffsetDateTime.now(), "scheduled");
    }

}
