package com.jaworski.dbcert.service.impl;

import com.jaworski.dbcert.dto.InstructorDto;
import com.jaworski.dbcert.entity.Instructor;
import com.jaworski.dbcert.mappers.InstructorMapper;
import com.jaworski.dbcert.repository.AccessRepository;
import com.jaworski.dbcert.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final AccessRepository<Instructor> instructorRepository;
    private final InstructorMapper instructorMapper;

    @Override
    public List<InstructorDto> getInstructors() throws SQLException, FileNotFoundException, ClassNotFoundException {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::mapToInstructorDto)
                .toList();
    }

}
