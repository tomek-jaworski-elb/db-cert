package com.jaworski.dbcert.service;

import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.mappers.StudentMapper;
import com.jaworski.dbcert.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentsRepository studentsRepository;
    private final StudentMapper studentMapper;

    public List<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException, FileNotFoundException {
        return studentsRepository.getAllStudents()
                .stream()
                .map(studentMapper::mapToStudentDTO)
                .filter(Objects::nonNull)
                .toList();
    }
}
