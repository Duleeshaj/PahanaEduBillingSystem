package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    boolean addCustomer(Customer customer) throws DaoException;
    boolean updateCustomer(Customer customer) throws DaoException;
    boolean deleteCustomer(int accountNumber) throws DaoException;
    Optional<Customer> getCustomerByAccountNumber(int accountNumber) throws DaoException;
    List<Customer> getAllCustomers() throws DaoException;
}
