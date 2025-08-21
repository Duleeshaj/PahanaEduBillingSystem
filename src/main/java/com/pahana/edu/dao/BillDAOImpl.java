package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.BillItemRequest;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {

    @Override
    public int createBill(int accountNumber, List<BillItemRequest> items) throws DaoException {
        if (items == null || items.isEmpty()) throw new DaoException("No items to bill");

        final String INS_BILL            = "INSERT INTO bills (account_number, total_amount) VALUES (?, 0)";
        final String SEL_ITEM_FOR_UPDATE = "SELECT price, stock FROM items WHERE item_id = ? FOR UPDATE";
        final String UPD_STOCK           = "UPDATE items SET stock = stock - ? WHERE item_id = ?";
        final String INS_BI              = "INSERT INTO bill_items (bill_id, item_id, qty, unit_price, line_total) VALUES (?,?,?,?,?)";
        final String UPD_BILL_TOT        = "UPDATE bills SET total_amount = ? WHERE bill_id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                // 1) Bill header
                int billId;
                try (PreparedStatement ps = con.prepareStatement(INS_BILL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, accountNumber);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("No bill id generated");
                        billId = rs.getInt(1);
                    }
                }

                double total = 0.0;

                // 2) Items
                for (BillItemRequest req : items) {
                    if (req.getQty() <= 0) throw new SQLException("Invalid qty for item " + req.getItemId());

                    double price;
                    int stock;

                    // lock row
                    try (PreparedStatement ps = con.prepareStatement(SEL_ITEM_FOR_UPDATE)) {
                        ps.setInt(1, req.getItemId());
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) throw new SQLException("Item not found: " + req.getItemId());
                            price = rs.getBigDecimal("price").doubleValue();
                            stock = rs.getInt("stock");
                        }
                    }

                    if (stock < req.getQty()) throw new SQLException("Insufficient stock for item " + req.getItemId());

                    double line = price * req.getQty();
                    total += line;

                    try (PreparedStatement ps = con.prepareStatement(INS_BI)) {
                        ps.setInt(1, billId);
                        ps.setInt(2, req.getItemId());
                        ps.setInt(3, req.getQty());
                        ps.setBigDecimal(4, java.math.BigDecimal.valueOf(price));
                        ps.setBigDecimal(5, java.math.BigDecimal.valueOf(line));
                        ps.executeUpdate();
                    }

                    try (PreparedStatement ps = con.prepareStatement(UPD_STOCK)) {
                        ps.setInt(1, req.getQty());
                        ps.setInt(2, req.getItemId());
                        ps.executeUpdate();
                    }
                }

                // 3) Total
                try (PreparedStatement ps = con.prepareStatement(UPD_BILL_TOT)) {
                    ps.setBigDecimal(1, java.math.BigDecimal.valueOf(total));
                    ps.setInt(2, billId);
                    ps.executeUpdate();
                }

                con.commit();
                return billId;

            } catch (SQLException e) {
                try { con.rollback(); } catch (SQLException ignore) {}
                throw new DaoException("createBill failed: " + e.getMessage(), e);
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignore) {}
            }
        } catch (SQLException e) {
            throw new DaoException("createBill connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Bill getBillById(int billId) throws DaoException {
        final String SQL = "SELECT bill_id, account_number, total_amount, bill_date " +
                "FROM bills WHERE bill_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Bill b = mapBill(rs);
                b.setItems(getBillItems(billId));
                return b;
            }
        } catch (SQLException e) {
            throw new DaoException("getBillById failed", e);
        }
    }

    @Override
    public List<Bill> getBillsByCustomer(int accountNumber) throws DaoException {
        final String SQL = "SELECT bill_id, account_number, total_amount, bill_date " +
                "FROM bills WHERE account_number = ? " +
                "ORDER BY bill_date DESC, bill_id DESC";
        List<Bill> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapBill(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new DaoException("getBillsByCustomer failed", e);
        }
    }

    @Override
    public List<Bill> getAllBills() throws DaoException {
        final String SQL = "SELECT bill_id, account_number, total_amount, bill_date " +
                "FROM bills ORDER BY bill_date DESC, bill_id DESC";
        List<Bill> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(mapBill(rs));
            return out;
        } catch (SQLException e) {
            throw new DaoException("getAllBills failed", e);
        }
    }

    @Override
    public List<BillItem> getBillItems(int billId) throws DaoException {
        // join to fetch the item name for UI
        final String SQL =
                "SELECT bi.bill_item_id, bi.bill_id, bi.item_id, i.name AS item_name, " +
                        "       bi.qty, bi.unit_price, bi.line_total " +
                        "FROM bill_items bi " +
                        "JOIN items i ON i.item_id = bi.item_id " +
                        "WHERE bi.bill_id = ?";
        List<BillItem> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BillItem li = new BillItem();
                    li.setBillItemId(rs.getInt("bill_item_id"));
                    li.setBillId(rs.getInt("bill_id"));
                    li.setItemId(rs.getInt("item_id"));
                    li.setItemName(rs.getString("item_name")); // <-- set the name
                    li.setQty(rs.getInt("qty"));
                    li.setUnitPrice(rs.getBigDecimal("unit_price").doubleValue());
                    li.setLineTotal(rs.getBigDecimal("line_total").doubleValue());
                    out.add(li);
                }
            }
            return out;
        } catch (SQLException e) {
            throw new DaoException("getBillItems failed", e);
        }
    }

    @Override
    public List<Bill> getBillsByDateRange(LocalDate from, LocalDate to) {
        // Interface doesn't declare throws; return an empty list on failure.
        final String SQL =
                "SELECT bill_id, account_number, total_amount, bill_date " +
                        "FROM bills " +
                        "WHERE bill_date >= ? AND bill_date < ? " +
                        "ORDER BY bill_date DESC, bill_id DESC";
        List<Bill> out = new ArrayList<>();
        // normalize 'to' as exclusive end (add 1 day)
        LocalDate toExclusive = (to == null) ? from : to.plusDays(1);

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setTimestamp(1, Timestamp.valueOf(from.atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(toExclusive.atStartOfDay()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapBill(rs));
            }
        } catch (SQLException ignore) {
            // swallow per interface; if you prefer, wrap in RuntimeException
        }
        return out;
    }

    // --- helpers ---

    private static Bill mapBill(ResultSet rs) throws SQLException {
        Bill b = new Bill();
        b.setBillId(rs.getInt("bill_id"));
        b.setAccountNumber(rs.getInt("account_number"));
        b.setTotalAmount(rs.getBigDecimal("total_amount").doubleValue());
        Timestamp ts = rs.getTimestamp("bill_date");
        if (ts != null) b.setBillDate(ts.toLocalDateTime());
        return b;
    }
}
