package com.jaworski.dbcert.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.rest.StudentsRestClient;
import com.jaworski.dbcert.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SchedulingTask {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulingTask.class);
    private final StudentsRestClient studentsRestClient;
    private final StudentService studentService;
    private final ApplicationContext appContext;
    private int retryCount = 0;

    @Value("${task.fixedRate}")
    private String taskFixedRate;

    @Value("${task.retryCountMax}")
    private int taskRetryCountMax;

    @Scheduled(fixedRateString = "${task.fixedRate}", initialDelayString = "${task.initialDelay}")
    public void scheduledTask() {
        try {
            List<StudentDTO> studentDTOList = studentService.getAllStudents();
            LOG.info("Read all students count: {}", studentDTOList.size());
            LOG.info("File updated. Sending updated students");
            studentsRestClient.sendStudents(studentDTOList);
            retryCount = 0;
            LOG.info("Exiting application");
            shutdownApplication(Thread.currentThread(), 0);
        } catch (SQLException | ClassNotFoundException | RestClientException | JsonProcessingException | FileNotFoundException e) {
            retryCount++;
            if (retryCount <= taskRetryCountMax) {
                LOG.warn("Error while sending data to rest client. Retrying in {} seconds. Attempt: {}",
                        Integer.parseInt(taskFixedRate) / 1_000, retryCount);
            } else {
                LOG.error("Error while sending data to rest client. Exiting application", e);
                shutdownApplication(Thread.currentThread(), 1);
            }
            LOG.error(e.getMessage(), e);
        }
    }

    private void shutdownApplication(Thread thread, int exitCode) {
        try {
            thread.interrupt();
            SpringApplication.exit(appContext, () -> exitCode);
        } catch (SecurityException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
