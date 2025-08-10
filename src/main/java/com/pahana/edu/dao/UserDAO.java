package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.User;
import java.util.List;

public interface UserDAO {
    boolean addUser(User user) throws DaoException;
    User getUserByUsernameAndPassword(String username, String password) throws DaoException;
    User getUserByUsername(String username) throws DaoException;  // <-- new method
    boolean updateUserActiveStatus(int userId, boolean isActive) throws DaoException;
    boolean updateUserPassword(int userId, String newPassword) throws DaoException;
    List<String> getAuditLogs() throws DaoException;
}
