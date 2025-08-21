package com.pahana.edu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL  = "jdbc:mysql://localhost:3306/pahanaedu?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "password"; // <-- set your real password

    public static Connection getConnection() {
        try {
            // Ensure the MySQL 8+ driver is registered
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Ensure mysql-connector-j is on the classpath.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get DB connection", e);
        }
    }
}
