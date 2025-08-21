package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;

public interface ConfigDAO {
    double getUnitRate() throws DaoException;
}
