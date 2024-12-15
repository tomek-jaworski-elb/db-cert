package com.jaworski.dbcert.repository;

import com.jaworski.dbcert.db.DataSourceConfiguration;
import com.jaworski.dbcert.db.TableKursmain;
import com.jaworski.dbcert.entity.Student;
import com.jaworski.dbcert.scheduling.SchedulingTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class StudentsRepository {

    private static final Logger LOG = LogManager.getLogger(SchedulingTask.class);

    private final DataSourceConfiguration dataSourceConfiguration;

    public StudentsRepository(DataSourceConfiguration dataSourceConfiguration) {
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    public Collection<Student> getAllStudents() throws SQLException, ClassNotFoundException {
        LOG.info("Get all students. {}", System.currentTimeMillis());
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
            int certNo = resultSet.getInt(TableKursmain.CERT_NO);
            String name = resultSet.getString(TableKursmain.FIRST_NAME);
            String surname = resultSet.getString(TableKursmain.SURNAME);
            Date dateBegine = resultSet.getDate(TableKursmain.DATE_BEGINE);
            Date dateEnd = resultSet.getDate(TableKursmain.DATE_END);
            String mrMs = resultSet.getString(TableKursmain.MR_MS);
            String certType = resultSet.getString(TableKursmain.CERT_TYPE);
            String courseNo = resultSet.getString(TableKursmain.COURSE_NO);
            Student student = new Student(certNo, name, surname, courseNo, dateBegine, dateEnd, mrMs, certType);
            result.add(student);
        }
        return result;
    }
}
