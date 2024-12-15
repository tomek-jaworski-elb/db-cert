package com.jaworski.dbcert.scheduling;

import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.mappers.StudentMapper;
import com.jaworski.dbcert.repository.StudentsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class SchedulingTask {

    private static final Logger LOG = LogManager.getLogger(SchedulingTask.class);
    private final StudentsRepository studentsRepository;
    private final StudentMapper studentMapper = new StudentMapper();

    public SchedulingTask(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
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
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
