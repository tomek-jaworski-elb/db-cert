package com.jaworski.dbcert.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.rest.StudentsRestClient;
import com.jaworski.dbcert.service.FileDBUpdateChecker;
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
    private final FileDBUpdateChecker fileDBUpdateChecker;

    public SchedulingTask(StudentsRestClient studentsRestClient, StudentService studentService, FileDBUpdateChecker fileDBUpdateChecker) {
        this.studentsRestClient = studentsRestClient;
        this.studentService = studentService;
        this.fileDBUpdateChecker = fileDBUpdateChecker;
    }

    @Scheduled(fixedRateString = "${task.fixedRate}", initialDelayString = "${task.initialDelay}")
    public void scheduledTask() {
        try {
            List<StudentDTO> studentDTOList = studentService.getAllStudents();
            LOG.info("Read all students count: {}", studentDTOList.size());
            if (fileDBUpdateChecker.isFileUpdated()) {
                LOG.info("File updated");
                studentsRestClient.sendStudents(studentDTOList);
            }
        } catch (SQLException | ClassNotFoundException | RestClientException | JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
