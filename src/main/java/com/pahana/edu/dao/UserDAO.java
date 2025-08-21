package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.User;
import java.util.List;

public interface UserDAO {
    boolean addUser(User user) throws DaoException;

    /** Returns the user if username exists, account is active, and password matches (SHA-256 HEX). */
    User getUserByUsernameAndPassword(String username, String password) throws DaoException;

    /** Returns user by username (password field will be hashed as stored). */
    User getUserByUsername(String username) throws DaoException;

    boolean updateUserActiveStatus(int userId, boolean isActive) throws DaoException;

    /** Expects a hashed password. */
    boolean updateUserPassword(int userId, String hashedPassword) throws DaoException;

    List<String> getAuditLogs() throws DaoException;

    /** List users by role (e.g., "STAFF"). */
    List<User> listByRole(String role) throws DaoException;
}
