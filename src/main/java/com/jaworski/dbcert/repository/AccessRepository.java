package com.jaworski.dbcert.repository;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface AccessRepository<T> {

    List<T> findAll() throws SQLException, ClassNotFoundException, FileNotFoundException;

}
