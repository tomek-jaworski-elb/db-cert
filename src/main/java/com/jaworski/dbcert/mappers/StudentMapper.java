package com.jaworski.dbcert.mappers;

import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class StudentMapper {

    private static final Logger LOG = LoggerFactory.getLogger(StudentMapper.class);

    public Student mapToStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setLastName(studentDTO.getLastName());
        student.setCourseNo(studentDTO.getCourseNo());
        student.setDateBegine(studentDTO.getDateBegine());
        student.setDateEnd(studentDTO.getDateEnd());
        student.setMrMs(studentDTO.getMrMs());
        student.setCertType(studentDTO.getCertType());
        String photo = studentDTO.getPhoto();
        InputStream stream = new ByteArrayInputStream(photo.getBytes(StandardCharsets.UTF_8));
        student.setPhoto(stream);
        return student;
    }

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
        InputStream photo = student.getPhoto();
        try {
            String base64Photo = Base64.getEncoder().encodeToString(photo.readAllBytes());
            studentDTO.setPhoto(base64Photo);
            return studentDTO;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
