package com.pahana.edu.service;

import com.pahana.edu.dao.UserDAO;
import com.pahana.edu.dao.UserDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.User;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    /** Add a new user to the system with proper validations and role-based permissions */
    public boolean addUser(User user, String addedByRole) throws ServiceException {
        // Validate password requirement:
        if (!"CUSTOMER".equalsIgnoreCase(user.getRole())) {
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new ServiceException("Password is required for role: " + user.getRole());
            }
        } else {
            // For CUSTOMER role, ignore password field (set null)
            user.setPassword(null);
        }

        try {
            if ("ADMIN".equalsIgnoreCase(addedByRole)) {
                // Admin can add STAFF or CUSTOMER
                if ("STAFF".equalsIgnoreCase(user.getRole()) || "CUSTOMER".equalsIgnoreCase(user.getRole())) {
                    return userDAO.addUser(user);
                } else {
                    throw new ServiceException("Permission denied: Admin cannot add role '" + user.getRole() + "'");
                }
            } else if ("STAFF".equalsIgnoreCase(addedByRole)) {
                // Staff can add CUSTOMER only
                if ("CUSTOMER".equalsIgnoreCase(user.getRole())) {
                    return userDAO.addUser(user);
                } else {
                    throw new ServiceException("Permission denied: Staff cannot add role '" + user.getRole() + "'");
                }
            } else {
                throw new ServiceException("Permission denied: Unknown role " + addedByRole);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while adding user", e);
        }
    }

    /** Authenticate a user by username and password */
    public User authenticateUser(String username, String password) throws ServiceException {
        try {
            return userDAO.getUserByUsernameAndPassword(username, password);
        } catch (DaoException e) {
            throw new ServiceException("Error during authentication", e);
        }
    }

    /** Get a user by username */
    public User getUserByUsername(String username) throws ServiceException {
        try {
            return userDAO.getUserByUsername(username);
        } catch (DaoException e) {
            throw new ServiceException("Error retrieving user by username", e);
        }
    }

    /** Enable or disable a user account */
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

    /** Reset a user's password */
    public boolean resetPassword(int userId, String newPassword, String performedByRole) throws ServiceException {
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            throw new ServiceException("Permission denied: Only Admin can reset passwords.");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new ServiceException("New password cannot be empty");
        }
        try {
            return userDAO.updateUserPassword(userId, newPassword);
        } catch (DaoException e) {
            throw new ServiceException("Error resetting user password", e);
        }
    }

    /** View audit logs */
    public void viewAuditLogs(String performedByRole) throws ServiceException {
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            throw new ServiceException("Permission denied: Only Admin can view audit logs.");
        }
        try {
            userDAO.getAuditLogs().forEach(System.out::println);
        } catch (DaoException e) {
            throw new ServiceException("Error retrieving audit logs", e);
        }
    }
}
