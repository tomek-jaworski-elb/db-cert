package com.jaworski.dbcert.service;

import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.mappers.StudentMapper;
import com.jaworski.dbcert.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Service
public class StudentService {

    private final StudentsRepository studentsRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentsRepository studentsRepository, StudentMapper studentMapper) {
        this.studentsRepository = studentsRepository;
        this.studentMapper = studentMapper;
    }

    public List<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        return studentsRepository.getAllStudents()
                .stream()
                .map(studentMapper::mapToStudentDTO)
                .toList();
    }
}
