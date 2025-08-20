package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.User;
import com.pahana.edu.util.PasswordUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean addUser(User user) throws DaoException {
        final String sql = "INSERT INTO users (username, password, role, active) VALUES (?, ?, ?, ?)";
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

    /**
     * DAO-level auth (optional): fetch by username (active=1) and verify with PasswordUtils.
     * Returns null if not found / inactive / password mismatch.
     */
    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws DaoException {
        final String sql = "SELECT user_id, username, password, role, active FROM users WHERE username = ? AND active = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

                String storedHash = rs.getString("password");
                // Use the same verification (SHA-256 → HEX UPPER)
                if (!PasswordUtils.verifyPassword(password, storedHash)) {
                    return null;
                }

                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        storedHash,                     // keep hashed password set
                        rs.getString("role"),
                        rs.getBoolean("active")
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to authenticate user at DAO", e);
        }
    }

    @Override
    public User getUserByUsername(String username) throws DaoException {
        final String sql = "SELECT user_id, username, password, role, active FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"), // hashed password (as stored)
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
        final String sql = "UPDATE users SET active = ? WHERE user_id = ?";
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
    public boolean updateUserPassword(int userId, String newHashedPassword) throws DaoException {
        final String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newHashedPassword); // already hashed in Service
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to reset user password", e);
        }
    }

    @Override
    public List<String> getAuditLogs() throws DaoException {
        final String sql = "SELECT action FROM audit_logs ORDER BY timestamp DESC";
        final List<String> logs = new ArrayList<>();
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
