package com.example.java_pong;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pongdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    private static Connection connection;

    // Private constructor
    private DatabaseConnector() {
    }

    //make sure we only get one instance
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (DatabaseConnector.class) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                }
            }
        }
        return connection;
    }
}
