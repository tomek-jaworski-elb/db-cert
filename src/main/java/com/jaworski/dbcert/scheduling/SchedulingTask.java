package com.jaworski.dbcert.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaworski.dbcert.dto.InstructorDto;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.resources.CustomResources;
import com.jaworski.dbcert.rest.AISRestClient;
import com.jaworski.dbcert.service.InstructorService;
import com.jaworski.dbcert.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final StudentService studentService;
    private final ApplicationContext appContext;
    private int retryCount = 0;
    private final CustomResources customResources;
    private final InstructorService instructorService;
    private final AISRestClient aisRestClient;

    @Scheduled(fixedRateString = "${task.fixedRate}", initialDelayString = "${task.initialDelay}")
    public void scheduledTask() {
        try {
            List<InstructorDto> instructors = instructorService.getInstructors();
            LOG.info("Read instructors count: {}", instructors.size());
            LOG.info("File updated. Sending updated instructors");
            aisRestClient.sendCollection(instructors);
            List<StudentDTO> students = studentService.getAllStudents();
            LOG.info("Read all students count: {}", students.size());
            LOG.info("File updated. Sending updated students");
            aisRestClient.sendCollection(students);
            retryCount = 0;
            LOG.info("Exiting application");
            shutdownApplication(Thread.currentThread(), 0);
        } catch (SQLException | ClassNotFoundException | RestClientException | JsonProcessingException | FileNotFoundException e) {
            retryCount++;
            if (retryCount <= customResources.getTaskRetryCountMax()) {
                LOG.warn("Error while sending data to rest client. Retrying in {} seconds. Attempt: {}",
                        Integer.parseInt(customResources.getTaskFixedRate()) / 1_000, retryCount);
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
