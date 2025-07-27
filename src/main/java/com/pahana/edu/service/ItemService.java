package com.pahana.edu.service;

import com.pahana.edu.dao.ItemDAO;
import com.pahana.edu.dao.ItemDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Item;

import java.util.List;
import java.util.Optional;

public class ItemService {

    private final ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAOImpl();
    }

    public boolean addItem(Item item) throws ServiceException {
        try {
            return itemDAO.addItem(item);
        } catch (DaoException e) {
            throw new ServiceException("Error while adding item", e);
        }
    }

    public boolean updateItem(Item item) throws ServiceException {
        try {
            return itemDAO.updateItem(item);
        } catch (DaoException e) {
            throw new ServiceException("Error while updating item", e);
        }
    }

    public boolean deleteItem(int itemId) throws ServiceException {
        try {
            return itemDAO.deleteItem(itemId);
        } catch (DaoException e) {
            throw new ServiceException("Error while deleting item", e);
        }
    }

    public Optional<Item> getItemById(int itemId) throws ServiceException {
        try {
            return itemDAO.getItemById(itemId);
        } catch (DaoException e) {
            throw new ServiceException("Error while fetching item", e);
        }
    }

    public List<Item> getAllItems() throws ServiceException {
        try {
            return itemDAO.getAllItems();
        } catch (DaoException e) {
            throw new ServiceException("Error while fetching all items", e);
        }
    }
}
