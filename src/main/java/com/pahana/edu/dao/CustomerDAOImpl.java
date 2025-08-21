package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean addCustomer(Customer c) throws DaoException {
        final String sql = "{ CALL sp_addCustomer(?, ?, ?, ?, ?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, c.getAccountNumber());
            cs.setString(2, c.getName());
            cs.setString(3, c.getAddress());
            cs.setString(4, c.getTelephone());
            cs.setString(5, c.getEmail());
            cs.execute();
            return true;
        } catch (SQLException e) {
            throw wrap("addCustomer failed", e);
        }
    }

    @Override
    public Customer getCustomerByAccountNumber(int accountNumber) throws DaoException {
        final String sql = "{ CALL sp_getCustomerByAccountNumber(?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, accountNumber);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                return null;
            }
        } catch (SQLException e) {
            throw wrap("getCustomerByAccountNumber failed", e);
        }
    }

    @Override
    public boolean updateCustomer(Customer c) throws DaoException {
        final String sql = "{ CALL sp_updateCustomer(?, ?, ?, ?, ?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, c.getAccountNumber());
            cs.setString(2, c.getName());
            cs.setString(3, c.getAddress());
            cs.setString(4, c.getTelephone());
            cs.setString(5, c.getEmail());
            cs.execute();
            return true;
        } catch (SQLException e) {
            throw wrap("updateCustomer failed", e);
        }
    }

    @Override
    public boolean deleteCustomer(int accountNumber) throws DaoException {
        final String sql = "{ CALL sp_deleteCustomer(?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, accountNumber);
            cs.execute();
            return true;
        } catch (SQLException e) {
            throw wrap("deleteCustomer failed", e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws DaoException {
        final String sql = "{ CALL sp_getAllCustomers() }";
        final List<Customer> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) out.add(mapRow(rs));
            return out;
        } catch (SQLException e) {
            throw wrap("getAllCustomers failed", e);
        }
    }

    @Override
    public List<Customer> searchCustomersByName(String namePart) throws DaoException {
        final String sql =
                "SELECT account_number, name, address, telephone, email " +
                        "FROM customers WHERE name LIKE ? ORDER BY name ASC";
        final List<Customer> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (namePart == null ? "" : namePart) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
            return out;
        } catch (SQLException e) {
            throw wrap("searchCustomersByName failed", e);
        }
    }

    @Override
    public boolean doesCustomerExist(int accountNumber) {
        final String sql = "SELECT 1 FROM customers WHERE account_number = ? LIMIT 1";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException("doesCustomerExist failed: " + e.getMessage(), e);
        }
    }

    private static Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setAccountNumber(rs.getInt("account_number"));
        c.setName(rs.getString("name"));
        c.setAddress(rs.getString("address"));
        c.setTelephone(rs.getString("telephone"));
        c.setEmail(rs.getString("email"));
        return c;
    }

    private static DaoException wrap(String msg, SQLException e) {
        return new DaoException(msg + ": " + e.getMessage(), e);
    }
}
