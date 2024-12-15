package com.jaworski.dbcert.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataSourceConfiguration {

    @Value("${db.file.path}")
    private String filePath;

    private static final Logger LOG = LogManager.getLogger(DataSourceConfiguration.class);
    private static final String NET_UCANACCESS_JDBC_UCANACCESS_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
    private static final String JDBC_UCANACCESS = "jdbc:ucanaccess://";


    public Connection getSqlConnection() throws SQLException, ClassNotFoundException {
        System.setProperty("hsqldb.method_class_names", "net.ucanaccess.converters.*"); // see http://hsqldb.org/doc/2.0/guide/sqlroutines-chapt.html#src_jrt_access_control
        Class.forName(NET_UCANACCESS_JDBC_UCANACCESS_DRIVER);
        Path path = getPathToFileDB();
        return DriverManager.getConnection(JDBC_UCANACCESS + path + ";memory=false");
    }

    private Path getPathToFileDB() {
        Path path = Path.of(".");
        String fileDbPath = filePath;
        path = path.resolve(fileDbPath);
        if (!path.toFile().isFile()) {
            LOG.error("File not found: {}", path);
            throw new RuntimeException("File not found: " + path);
        }
        return path;
    }
}
