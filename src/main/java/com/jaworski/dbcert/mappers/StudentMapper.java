package com.jaworski.dbcert.mappers;

import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.dto.StudentDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

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
        return student;
    }

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
        return studentDTO;
    }
}
