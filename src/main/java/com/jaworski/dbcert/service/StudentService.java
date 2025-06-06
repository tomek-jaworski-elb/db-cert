package com.jaworski.dbcert.service;

import com.jaworski.dbcert.dto.StudentDTO;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    List<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException, FileNotFoundException;

}
