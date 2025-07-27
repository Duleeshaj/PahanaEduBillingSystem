package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDAO {
    boolean addItem(Item item) throws DaoException;
    boolean updateItem(Item item) throws DaoException;
    boolean deleteItem(int itemId) throws DaoException;
    Optional<Item> getItemById(int itemId) throws DaoException;
    List<Item> getAllItems() throws DaoException;
}
