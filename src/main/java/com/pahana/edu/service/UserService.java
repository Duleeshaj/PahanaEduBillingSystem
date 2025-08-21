package com.pahana.edu.service;

import com.pahana.edu.dao.UserDAO;
import com.pahana.edu.dao.UserDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.User;
import com.pahana.edu.util.PasswordUtils;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    // Add user with role-based permission
    public boolean addUser(User user, String addedByRole) throws ServiceException {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ServiceException("Username cannot be empty");
        }
        // Role-based restrictions
        if ("ADMIN".equalsIgnoreCase(addedByRole)) {
            if (!user.getRole().equalsIgnoreCase("STAFF") &&
                    !user.getRole().equalsIgnoreCase("CUSTOMER")) {
                throw new ServiceException("Admin can only add Staff or Customer");
            }
        } else if ("STAFF".equalsIgnoreCase(addedByRole)) {
            if (!user.getRole().equalsIgnoreCase("CUSTOMER")) {
                throw new ServiceException("Staff can only add Customers");
            }
        } else {
            throw new ServiceException("Permission denied: Unknown role");
        }

        // Duplicate username check (friendlier than DB error)
        try {
            User existing = userDAO.getUserByUsername(user.getUsername());
            if (existing != null) {
                throw new ServiceException("Username already exists. Choose another.");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error checking existing username", e);
        }

        // Hash & save
        try {
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
            }
            return userDAO.addUser(user);
        } catch (DaoException e) {
            // Fall back to readable message if DB still rejects (e.g., unique index)
            throw new ServiceException("Error while adding user", e);
        } catch (Exception e) {
            throw new ServiceException("Password hashing error", e);
        }
    }


    /** Admin-only: list staff users */
    public List<User> listStaff(String performedByRole) throws ServiceException {
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            throw new ServiceException("Permission denied: Only Admin can view staff.");
        }
        try {
            return userDAO.listByRole("STAFF");
        } catch (DaoException e) {
            throw new ServiceException("Error fetching staff", e);
        }
    }

    // Authenticate user
    public User authenticateUser(String username, String password) throws ServiceException {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new ServiceException("Invalid username or password");
        }
        try {
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                throw new ServiceException("Invalid username or password");
            }
            if (!user.isActive()) {
                throw new ServiceException("Account is inactive");
            }

            String storedHash = user.getPassword();

            // ✅ use positive condition
            if (PasswordUtils.verifyPassword(password, storedHash)) {
                return user;
            }

            // no match
            throw new ServiceException("Invalid username or password");

        } catch (DaoException e) {
            throw new ServiceException("Error during authentication", e);
        }
    }


    // Enable/Disable users (Admin only)
    public boolean setUserActiveStatus(int userId, boolean isActive, String performedByRole) throws ServiceException {
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            throw new ServiceException("Permission denied: Only Admin can enable/disable users.");
        }
        try {
            return userDAO.updateUserActiveStatus(userId, isActive);
        } catch (DaoException e) {
            throw new ServiceException("Error updating user active status", e);
        }
    }

    // Reset password (Admin only)
    public boolean resetPassword(int userId, String newPassword, String performedByRole) throws ServiceException {
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            throw new ServiceException("Permission denied: Only Admin can reset passwords.");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new ServiceException("New password cannot be empty");
        }
        try {
            String hashed = PasswordUtils.hashPassword(newPassword);
            return userDAO.updateUserPassword(userId, hashed);
        } catch (DaoException e) {
            throw new ServiceException("Error resetting user password", e);
        } catch (Exception e) {
            throw new ServiceException("Password hashing error", e);
        }
    }

    // View audit logs (Admin only)
    public List<String> viewAuditLogs(String performedByRole) throws ServiceException {
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            throw new ServiceException("Permission denied: Only Admin can view audit logs.");
        }
        try {
            return userDAO.getAuditLogs();
        } catch (DaoException e) {
            throw new ServiceException("Error retrieving audit logs", e);
        }
    }
}
