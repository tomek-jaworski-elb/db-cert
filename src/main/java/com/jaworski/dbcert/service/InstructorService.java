package com.jaworski.dbcert.service;

import com.jaworski.dbcert.dto.InstructorDto;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface InstructorService {

  List<InstructorDto> getInstructors() throws SQLException, FileNotFoundException, ClassNotFoundException;

}
