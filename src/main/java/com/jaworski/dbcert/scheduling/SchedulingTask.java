package com.jaworski.dbcert.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.rest.StudentsRestClient;
import com.jaworski.dbcert.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.sql.SQLException;
import java.util.List;

@Component
public class SchedulingTask {

    private static final Logger LOG = LogManager.getLogger(SchedulingTask.class);
    private final StudentsRestClient studentsRestClient;
    private final StudentService studentService;

    public SchedulingTask(StudentsRestClient studentsRestClient, StudentService studentService) {
        this.studentsRestClient = studentsRestClient;
        this.studentService = studentService;
    }

    @Scheduled(fixedRateString = "${task.fixedRate}", initialDelayString = "${task.initialDelay}")
    public void scheduledTask() {
        LOG.info("Scheduled task executed. {}", System.currentTimeMillis());
        try {
            List<StudentDTO> studentDTOList = studentService.getAllStudents();
            LOG.info("All students count: {}", studentDTOList.size());
            studentsRestClient.sendStudents(studentDTOList);
        } catch (SQLException | ClassNotFoundException | RestClientException | JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
