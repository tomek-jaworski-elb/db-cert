package com.jaworski.dbcert;

import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.mappers.StudentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StudentMapper.class)
class DbCertApplicationTests {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(studentMapper);
    }

    @Test
    void testEmptyStudent() {
        Student student = new Student();
        StudentDTO studentDTO = studentMapper.mapToStudentDTO(student);
        Assertions.assertNotNull(studentDTO);
    }

    @Test
    void testNull() {
        Assertions.assertThrows(NullPointerException.class, () -> studentMapper.mapToStudentDTO(null));
    }
}
