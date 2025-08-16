package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean addUser(User user) throws DaoException {
        String sql = "INSERT INTO users (username, password, role, active) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // hashed already in Service
            stmt.setString(3, user.getRole());
            stmt.setBoolean(4, true); // new users are active by default
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to add user", e);
        }
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws DaoException {
        return null;
    }

    @Override
    public User getUserByUsername(String username) throws DaoException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"), // hashed password
                            rs.getString("role"),
                            rs.getBoolean("active")
                    );
                }
            }
            return null;

        } catch (SQLException e) {
            throw new DaoException("Failed to get user by username", e);
        }
    }

    @Override
    public boolean updateUserActiveStatus(int userId, boolean isActive) throws DaoException {
        String sql = "UPDATE users SET active = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isActive);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to update user active status", e);
        }
    }

    @Override
    public boolean updateUserPassword(int userId, String newPassword) throws DaoException {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword); // already hashed in Service
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to reset user password", e);
        }
    }

    @Override
    public List<String> getAuditLogs() throws DaoException {
        List<String> logs = new ArrayList<>();
        String sql = "SELECT action FROM audit_logs ORDER BY timestamp DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                logs.add(rs.getString("action"));
            }
            return logs;

        } catch (SQLException e) {
            throw new DaoException("Failed to retrieve audit logs", e);
        }
    }
}
