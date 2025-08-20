package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Item;

import java.util.List;

public interface ItemDAO {
    int addItem(Item item) throws DaoException;                      // returns new id
    Item getItemById(int itemId) throws DaoException;
    boolean updateItem(Item item) throws DaoException;
    boolean deleteItem(int itemId) throws DaoException;
    List<Item> getAllItems() throws DaoException;
    List<Item> searchItemsByName(String namePart) throws DaoException;
}
