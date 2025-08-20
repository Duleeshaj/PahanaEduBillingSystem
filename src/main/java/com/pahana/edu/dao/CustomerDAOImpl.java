package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation. Uses stored procedures where available:
 *  - sp_addCustomer(p_account_number, p_name, p_address, p_telephone, p_units)
 *  - sp_getCustomerByAccountNumber(p_account_number)
 *  - sp_updateCustomer(p_account_number, p_name, p_address, p_telephone, p_units)
 *  - sp_deleteCustomer(p_account_number)
 *  - sp_getAllCustomers()
 *
 * For name search we use a simple SELECT with LIKE (no extra SP required).
 *
 * Assumes a DatabaseConnection class exists that returns a java.sql.Connection.
 */
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
            cs.setInt(5, c.getUnitsConsumed());

            cs.execute();
            return true; // SP will SIGNAL on duplicate; otherwise success

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
                if (rs.next()) {
                    return mapRow(rs);
                }
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
            cs.setInt(5, c.getUnitsConsumed());

            cs.execute();
            // If no row affected, the SP raises SQLSTATE '02000' → SQLException
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
        // Simple LIKE-based search (case-insensitive if collation permits)
        final String sql = "SELECT account_number, name, address, telephone, units_consumed " +
                "FROM customers WHERE name LIKE ? ORDER BY name ASC";
        final List<Customer> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + namePart + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
            return out;

        } catch (SQLException e) {
            throw wrap("searchCustomersByName failed", e);
        }
    }

    // --- helpers ---

    private static Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setAccountNumber(rs.getInt("account_number"));
        c.setName(rs.getString("name"));
        c.setAddress(rs.getString("address"));
        c.setTelephone(rs.getString("telephone"));
        c.setUnitsConsumed(rs.getInt("units_consumed"));
        return c;
    }

    private static DaoException wrap(String msg, SQLException e) {
        return new DaoException(msg + ": " + e.getMessage(), e);
    }
}
