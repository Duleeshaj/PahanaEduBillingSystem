package com.pahana.edu.service;

import com.pahana.edu.dao.ItemDAO;
import com.pahana.edu.dao.ItemDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class ItemService {

    private final ItemDAO dao = new ItemDAOImpl();

    public int addItem(Item item) throws ServiceException {
        validate(item, false);
        try {
            return dao.addItem(item);
        } catch (DaoException e) {
            throw new ServiceException("Error adding item", e);
        }
    }

    public Item getItemById(int id) throws ServiceException {
        try {
            return dao.getItemById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error loading item", e);
        }
    }

    public boolean updateItem(Item item) throws ServiceException {
        validate(item, true);
        try {
            return dao.updateItem(item);
        } catch (DaoException e) {
            throw new ServiceException("Error updating item", e);
        }
    }

    public boolean deleteItem(int id) throws ServiceException {
        try {
            return dao.deleteItem(id);
        } catch (DaoException e) {
            throw new ServiceException("Error deleting item", e);
        }
    }

    public List<Item> getAllItems() throws ServiceException {
        try {
            return dao.getAllItems();
        } catch (DaoException e) {
            throw new ServiceException("Error listing items", e);
        }
    }

    public List<Item> searchItemsByName(String q) throws ServiceException {
        try {
            return dao.searchItemsByName(q == null ? "" : q.trim());
        } catch (DaoException e) {
            throw new ServiceException("Error searching items", e);
        }
    }

    private void validate(Item i, boolean requireId) throws ServiceException {
        if (requireId && i.getItemId() <= 0) throw new ServiceException("Invalid item id");
        if (i.getName() == null || i.getName().trim().isEmpty()) throw new ServiceException("Name is required");
        if (i.getPrice() == null || i.getPrice().compareTo(BigDecimal.ZERO) < 0) throw new ServiceException("Price must be >= 0");
        if (i.getStock() < 0) throw new ServiceException("Stock must be >= 0");
    }
}
