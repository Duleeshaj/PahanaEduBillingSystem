package com.pahana.edu.service;

import com.pahana.edu.dao.CustomerDAO;
import com.pahana.edu.dao.CustomerDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAOImpl();
    }

    public boolean registerCustomer(Customer customer) throws ServiceException {
        try {
            // Check if customer already exists
            Optional<Customer> existing = customerDAO.getCustomerByAccountNumber(customer.getAccountNumber());
            if (existing.isPresent()) {
                System.out.println("⚠️ Customer with account number already exists!");
                return false;
            }

            return customerDAO.addCustomer(customer);

        } catch (DaoException e) {
            throw new ServiceException("Error while adding customer", e);
        }
    }


    public boolean updateCustomer(Customer customer) throws ServiceException {
        try {
            return customerDAO.updateCustomer(customer);
        } catch (DaoException e) {
            throw new ServiceException("Error while updating customer", e);
        }
    }

    public boolean deleteCustomer(int accountNumber) throws ServiceException {
        try {
            return customerDAO.deleteCustomer(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while deleting customer", e);
        }
    }

    public Optional<Customer> getCustomerByAccountNumber(int accountNumber) throws ServiceException {
        try {
            return customerDAO.getCustomerByAccountNumber(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while fetching customer", e);
        }
    }

    public List<Customer> getAllCustomers() throws ServiceException {
        try {
            return customerDAO.getAllCustomers();
        } catch (DaoException e) {
            throw new ServiceException("Error while fetching all customers", e);
        }
    }
}
