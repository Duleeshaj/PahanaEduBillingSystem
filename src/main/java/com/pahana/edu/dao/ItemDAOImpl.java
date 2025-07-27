package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean addItem(Item item) throws DaoException {
        String sql = "INSERT INTO items (name, description, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getStock());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to add item", e);
        }
    }

    @Override
    public boolean updateItem(Item item) throws DaoException {
        String sql = "UPDATE items SET name=?, description=?, price=?, stock=? WHERE item_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getStock());
            stmt.setInt(5, item.getItemId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to update item", e);
        }
    }

    @Override
    public boolean deleteItem(int itemId) throws DaoException {
        String sql = "DELETE FROM items WHERE item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to delete item", e);
        }
    }

    @Override
    public Optional<Item> getItemById(int itemId) throws DaoException {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item(
                            rs.getInt("item_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                    return Optional.of(item);
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch item", e);
        }
    }

    @Override
    public List<Item> getAllItems() throws DaoException {
        String sql = "SELECT * FROM items ORDER BY item_id";
        List<Item> itemList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                itemList.add(item);
            }

            return itemList;

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch all items", e);
        }
    }
}
