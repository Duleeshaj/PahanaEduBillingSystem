package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public int addItem(Item item) throws DaoException {
        final String call = "{ CALL sp_addItem(?,?,?,?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(call)) {
            cs.setString(1, item.getName());
            cs.setString(2, item.getDescription());
            cs.setBigDecimal(3, item.getPrice());
            cs.setInt(4, item.getStock());
            boolean hasRs = cs.execute();
            if (hasRs) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) return rs.getInt("new_id");
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException("addItem failed", e);
        }
    }

    @Override
    public Item getItemById(int itemId) throws DaoException {
        final String call = "{ CALL sp_getItemById(?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(call)) {
            cs.setInt(1, itemId);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("getItemById failed", e);
        }
    }

    @Override
    public boolean updateItem(Item item) throws DaoException {
        final String call = "{ CALL sp_updateItem(?,?,?,?,?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(call)) {
            cs.setInt(1, item.getItemId());
            cs.setString(2, item.getName());
            cs.setString(3, item.getDescription());
            cs.setBigDecimal(4, item.getPrice());
            cs.setInt(5, item.getStock());
            boolean hasRs = cs.execute();
            if (hasRs) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) return rs.getInt("affected") > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException("updateItem failed", e);
        }
    }

    @Override
    public boolean deleteItem(int itemId) throws DaoException {
        final String call = "{ CALL sp_deleteItem(?) }";
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(call)) {
            cs.setInt(1, itemId);
            boolean hasRs = cs.execute();
            if (hasRs) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) return rs.getInt("affected") > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException("deleteItem failed", e);
        }
    }

    @Override
    public List<Item> getAllItems() throws DaoException {
        final String call = "{ CALL sp_getAllItems() }";
        List<Item> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             CallableStatement cs = con.prepareCall(call);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) out.add(mapRow(rs));
            return out;
        } catch (SQLException e) {
            throw new DaoException("getAllItems failed", e);
        }
    }

    @Override
    public List<Item> searchItemsByName(String namePart) throws DaoException {
        // lightweight direct SQL for search (OK per rules)
        final String sql = "SELECT item_id, name, description, price, stock FROM items WHERE name LIKE ? ORDER BY name ASC";
        List<Item> out = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + namePart + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new DaoException("searchItemsByName failed", e);
        }
    }

    private static Item mapRow(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("item_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("stock")
        );
    }
}
