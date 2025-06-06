package com.jaworski.dbcert.service.impl;

import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.mappers.StudentMapper;
import com.jaworski.dbcert.repository.AccessRepository;
import com.jaworski.dbcert.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final AccessRepository<Student> studentsRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException, FileNotFoundException {
      return studentsRepository.findAll()
                .stream()
                .map(studentMapper::mapToStudentDTO)
                .filter(Objects::nonNull)
                .toList();
    }
}
