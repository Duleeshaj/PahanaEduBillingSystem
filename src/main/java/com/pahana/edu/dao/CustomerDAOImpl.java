package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean addCustomer(Customer customer) throws DaoException {
        String query = "{CALL sp_addCustomer(?, ?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setInt(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setInt(5, customer.getUnitsConsumed());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to add customer", e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) throws DaoException {
        String query = "{CALL sp_updateCustomer(?, ?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setInt(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setInt(5, customer.getUnitsConsumed());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to update customer", e);
        }
    }

    @Override
    public boolean deleteCustomer(int accountNumber) throws DaoException {
        String query = "{CALL sp_deleteCustomer(?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setInt(1, accountNumber);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to delete customer", e);
        }
    }

    @Override
    public Optional<Customer> getCustomerByAccountNumber(int accountNumber) throws DaoException {
        String query = "{CALL sp_getCustomerByAccountNumber(?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setInt(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch customer", e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws DaoException {
        String query = "{CALL sp_getAllCustomers()}";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapRow(rs));
            }
            return customers;

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch all customers", e);
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setAccountNumber(rs.getInt("account_number"));
        c.setName(rs.getString("name"));
        c.setAddress(rs.getString("address"));
        c.setTelephone(rs.getString("telephone"));
        c.setUnitsConsumed(rs.getInt("units_consumed"));
        return c;
    }
}
