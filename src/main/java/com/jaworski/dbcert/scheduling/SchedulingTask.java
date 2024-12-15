package com.jaworski.dbcert.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.mappers.StudentMapper;
import com.jaworski.dbcert.repository.StudentsRepository;
import com.jaworski.dbcert.rest.StudentsRestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class SchedulingTask {

    private static final Logger LOG = LogManager.getLogger(SchedulingTask.class);
    private final StudentsRepository studentsRepository;
    private final StudentMapper studentMapper = new StudentMapper();
    private final StudentsRestClient studentsRestClient;

    public SchedulingTask(StudentsRepository studentsRepository, StudentsRestClient studentsRestClient) {
        this.studentsRepository = studentsRepository;
        this.studentsRestClient = studentsRestClient;
    }


    @Scheduled(fixedRateString = "${task.fixedRate}", initialDelayString = "${task.initialDelay}")
    public void scheduledTask() {
        LOG.info("Scheduled task executed. {}", System.currentTimeMillis());
        try {
            Collection<Student> allStudents = studentsRepository.getAllStudents();
            List<StudentDTO> list = allStudents.stream()
                    .map(studentMapper::mapToStudentDTO)
                    .toList();

            LOG.info("All students count: {}", list.size());
            studentsRestClient.sendStudents(list);
        } catch (SQLException | ClassNotFoundException | RestClientException | JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
