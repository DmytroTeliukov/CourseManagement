package com.dteliukov.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
    private static final Logger logger = LogManager.getLogger(MySqlConnection.class);
    private static final String URL = "jdbc:mysql://localhost:3306/course_database";
    private static final String USER = "root";
    private static final String PASSWORD = "JEsgWz28Cg56_*";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connected to database!");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}
