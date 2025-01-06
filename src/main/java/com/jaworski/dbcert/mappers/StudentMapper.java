package com.jaworski.dbcert.mappers;

import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Component
public class StudentMapper {

    private static final Logger LOG = LoggerFactory.getLogger(StudentMapper.class);

    @Nullable
    public StudentDTO mapToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setCourseNo(student.getCourseNo());
        studentDTO.setDateBegine(student.getDateBegine());
        studentDTO.setDateEnd(student.getDateEnd());
        studentDTO.setMrMs(student.getMrMs());
        studentDTO.setCertType(student.getCertType());
        Blob photo = student.getPhoto();
        try {
            String base64Photo = Base64.getEncoder().encodeToString(photo.getBytes(1, (int) photo.length()));
            studentDTO.setPhoto(base64Photo);
            return studentDTO;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
