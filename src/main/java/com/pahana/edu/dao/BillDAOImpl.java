package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {

    @Override
    public boolean addBill(Bill bill) throws DaoException {
        final String call = "{ CALL sp_addBill(?, ?, ?, ?) }";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, bill.getAccountNumber());
            cs.setInt(2, bill.getUnitsConsumed());
            cs.setBigDecimal(3, java.math.BigDecimal.valueOf(bill.getUnitRate()));
            cs.registerOutParameter(4, Types.INTEGER);

            cs.execute();

            int newId = cs.getInt(4);
            if (newId > 0) {
                bill.setBillId(newId);
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new DaoException("Failed to insert bill via sp_addBill", e);
        }
    }

    @Override
    public List<Bill> getBillsByCustomer(int accountNumber) throws DaoException {
        final String call = "{ CALL sp_getBillsByCustomer(?) }";
        List<Bill> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, accountNumber);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch bills via sp_getBillsByCustomer", e);
        }
    }

    @Override
    public Bill getBillById(int billId) throws DaoException {
        final String call = "{ CALL sp_getBillById(?) }";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, billId);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch bill via sp_getBillById", e);
        }
    }

    @Override
    public List<Bill> getAllBills() throws DaoException {
        final String call = "{ CALL sp_getAllBills() }";
        List<Bill> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(call);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch all bills via sp_getAllBills", e);
        }
    }

    private static com.pahana.edu.model.Bill mapRow(ResultSet rs) throws SQLException {
        com.pahana.edu.model.Bill b = new com.pahana.edu.model.Bill();
        b.setBillId(rs.getInt("bill_id"));
        b.setAccountNumber(rs.getInt("account_number"));
        b.setUnitsConsumed(rs.getInt("units_consumed"));
        b.setUnitRate(rs.getDouble("unit_rate"));
        b.setTotalAmount(rs.getDouble("total_amount"));
        Timestamp ts = rs.getTimestamp("created_at");
        b.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);
        return b;
    }
}
