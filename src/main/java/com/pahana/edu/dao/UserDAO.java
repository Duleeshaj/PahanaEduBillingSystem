package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.User;

import java.util.Optional;

public interface UserDAO {
    boolean registerUser(User user) throws DaoException;
    Optional<User> login(String username, String password) throws DaoException;
}
