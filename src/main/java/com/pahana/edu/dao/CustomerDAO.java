package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Customer;

import java.util.List;

public interface CustomerDAO {

    boolean addCustomer(Customer customer) throws DaoException;

    Customer getCustomerByAccountNumber(int accountNumber) throws DaoException;

    boolean updateCustomer(Customer customer) throws DaoException;

    boolean deleteCustomer(int accountNumber) throws DaoException;

    List<Customer> getAllCustomers() throws DaoException;

    List<Customer> searchCustomersByName(String namePart) throws DaoException;
}
