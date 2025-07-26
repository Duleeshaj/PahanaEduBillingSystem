package com.pahana.edu.service;

import com.pahana.edu.dao.UserDAO;
import com.pahana.edu.dao.UserDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.User;

import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public boolean registerUser(User user) throws ServiceException {
        try {
            return userDAO.registerUser(user);
        } catch (DaoException e) {
            throw new ServiceException("Error while registering user", e);
        }
    }

    public Optional<User> login(String username, String password) throws ServiceException {
        try {
            return userDAO.login(username, password);
        } catch (DaoException e) {
            throw new ServiceException("Error during login", e);
        }
    }
}
