package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Bill;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {

    @Override
    public boolean addBill(Bill bill) throws DaoException {
        String sql = "INSERT INTO bills (account_number, units_consumed, unit_rate, total_amount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bill.getAccountNumber());
            stmt.setInt(2, bill.getUnitsConsumed());
            stmt.setDouble(3, bill.getUnitRate());
            stmt.setDouble(4, bill.getTotalAmount());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to add bill", e);
        }
    }

    @Override
    public List<Bill> getBillsByCustomer(int accountNumber) throws DaoException {
        String sql = "SELECT * FROM bills WHERE account_number = ? ORDER BY bill_date DESC";
        List<Bill> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setAccountNumber(rs.getInt("account_number"));
                    bill.setUnitsConsumed(rs.getInt("units_consumed"));
                    bill.setUnitRate(rs.getDouble("unit_rate"));
                    bill.setTotalAmount(rs.getDouble("total_amount"));
                    bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                    list.add(bill);
                }
            }

            return list;

        } catch (SQLException e) {
            throw new DaoException("Failed to get bills", e);
        }
    }
}
