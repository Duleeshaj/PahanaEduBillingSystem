package com.pahana.edu.service;

import com.pahana.edu.dao.CustomerDAO;
import com.pahana.edu.dao.CustomerDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAOImpl();
    }

    /** Create a new customer (validates required fields). */
    public boolean addCustomer(Customer customer) throws ServiceException {
        try {
            validateCustomerForCreateOrUpdate(customer);
            return customerDAO.addCustomer(customer);
        } catch (DaoException e) {
            throw new ServiceException("Error while adding customer", e);
        }
    }

    /** Update existing customer by account number. */
    public boolean updateCustomer(Customer customer) throws ServiceException {
        try {
            validateCustomerForCreateOrUpdate(customer);
            return customerDAO.updateCustomer(customer);
        } catch (DaoException e) {
            throw new ServiceException("Error while updating customer", e);
        }
    }

    /** Delete customer by account number. */
    public boolean deleteCustomer(int accountNumber) throws ServiceException {
        try {
            if (accountNumber <= 0) throw new ServiceException("Invalid account number");
            return customerDAO.deleteCustomer(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while deleting customer", e);
        }
    }

    /** Get one customer; returns null if not found. */
    public Customer getCustomerByAccountNumber(int accountNumber) throws ServiceException {
        try {
            if (accountNumber <= 0) throw new ServiceException("Invalid account number");
            return customerDAO.getCustomerByAccountNumber(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while fetching customer", e);
        }
    }

    /** List all customers. */
    public List<Customer> getAllCustomers() throws ServiceException {
        try {
            return customerDAO.getAllCustomers();
        } catch (DaoException e) {
            throw new ServiceException("Error while fetching all customers", e);
        }
    }

    /** Name-contains search; returns all if query blank. */
    public List<Customer> searchCustomersByName(String namePart) throws ServiceException {
        try {
            if (isBlank(namePart)) return getAllCustomers();
            return customerDAO.searchCustomersByName(namePart.trim());
        } catch (DaoException e) {
            throw new ServiceException("Error while searching customers", e);
        }
    }

    // --- helpers ---

    private static void validateCustomerForCreateOrUpdate(Customer c) throws ServiceException {
        if (c == null) throw new ServiceException("Customer cannot be null");
        if (c.getAccountNumber() <= 0) throw new ServiceException("Invalid account number");

        if (isBlank(c.getName())) throw new ServiceException("Name is required");
        if (isBlank(c.getAddress())) throw new ServiceException("Address is required");

        if (isBlank(c.getTelephone())) throw new ServiceException("Telephone is required");
        if (!c.getTelephone().matches("^[0-9+\\-() ]{7,20}$")) {
            throw new ServiceException("Telephone format is invalid");
        }

        if (isBlank(c.getEmail())) throw new ServiceException("Email is required");
        // Simple email format check; keeps things dependency-free.
        if (!c.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ServiceException("Email format is invalid");
        }
        if (c.getEmail().length() > 120) {
            throw new ServiceException("Email is too long");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
