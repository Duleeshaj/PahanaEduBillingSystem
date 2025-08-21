package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDAOImpl implements ConfigDAO {

    @Override
    public double getUnitRate() throws DaoException {
        String sql = "SELECT config_value FROM configurations WHERE config_key = 'unit_rate'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return Double.parseDouble(rs.getString("config_value"));
            } else {
                throw new DaoException("Unit rate not found in configuration");
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to retrieve unit rate", e);
        }
    }
}
