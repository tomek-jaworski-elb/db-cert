package com.jaworski.dbcert.repository;

import com.jaworski.dbcert.db.DataSourceConfiguration;
import com.jaworski.dbcert.db.TableKursmain;
import com.jaworski.dbcert.entity.Student;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class StudentsRepository {


    private final DataSourceConfiguration dataSourceConfiguration;

    public StudentsRepository(DataSourceConfiguration dataSourceConfiguration) {
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    public Collection<Student> getAllStudents() throws SQLException, ClassNotFoundException, FileNotFoundException {
        Connection sqlConnection = dataSourceConfiguration.getSqlConnection();
        Statement statement = sqlConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM [" + TableKursmain.TABLE_NAME + "]" +
                " ORDER BY [" + TableKursmain.DATE_END + "]" + " DESC, [" + TableKursmain.CERT_NO + "]" + " ASC");
        statement.close();
        return getResultCollection(resultSet);

    }

    private static Collection<Student> getResultCollection(ResultSet resultSet) throws SQLException {
        Collection<Student> result = new ArrayList<>();
        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getInt(TableKursmain.CERT_NO));
            student.setName(resultSet.getString(TableKursmain.FIRST_NAME));
            student.setLastName(resultSet.getString(TableKursmain.SURNAME));
            student.setDateBegine(resultSet.getDate(TableKursmain.DATE_BEGINE));
            student.setDateEnd(resultSet.getDate(TableKursmain.DATE_END));
            student.setMrMs(resultSet.getString(TableKursmain.MR_MS));
            student.setCertType(resultSet.getString(TableKursmain.CERT_TYPE));
            student.setCourseNo(resultSet.getString(TableKursmain.COURSE_NO));
            student.setPhoto(resultSet.getBlob(TableKursmain.PHOTO));
            student.setInstructors(resultSet.getString(TableKursmain.INSTRUCTORS));
            result.add(student);
        }
        return result;
    }
}
