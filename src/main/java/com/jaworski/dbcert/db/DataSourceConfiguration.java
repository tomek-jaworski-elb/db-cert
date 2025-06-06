package com.jaworski.dbcert.db;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DataSourceConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfiguration.class);
    private static final String NET_UCANACCESS_JDBC_UCANACCESS_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
    private static final String JDBC_UCANACCESS = "jdbc:ucanaccess://";


    public Connection getSqlConnection(String filePath) throws SQLException, ClassNotFoundException, FileNotFoundException {
        System.setProperty("hsqldb.method_class_names", "net.ucanaccess.converters.*"); // see http://hsqldb.org/doc/2.0/guide/sqlroutines-chapt.html#src_jrt_access_control
        Class.forName(NET_UCANACCESS_JDBC_UCANACCESS_DRIVER);
        Path path = getPathToFileDB(filePath);
        return DriverManager.getConnection(JDBC_UCANACCESS + path + ";memory=false");
    }

    private Path getPathToFileDB(String filePath) throws FileNotFoundException {
        Path path = Path.of(".");
        path = path.resolve(filePath);
        if (!path.toFile().isFile()) {
            LOG.error("File not found: {}", path);
            throw new FileNotFoundException("File not found: " + path);
        }
        return path;
    }
}
