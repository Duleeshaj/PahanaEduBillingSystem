package com.pahana.edu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/pahanaedu";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            // Return a *new* connection each time (simpler, safer for now)
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // Convert to unchecked exception so callers don't need try/catch
            throw new RuntimeException("Failed to get DB connection", e);
        }
    }
}
