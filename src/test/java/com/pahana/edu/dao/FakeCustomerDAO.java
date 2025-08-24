package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Customer;

import java.util.*;
import java.util.stream.Collectors;

public class FakeCustomerDAO implements CustomerDAO {

    private final Map<Integer, Customer> byId = new HashMap<>();

    @Override
    public boolean addCustomer(Customer customer) throws DaoException {
        if (customer == null) throw new DaoException("Customer is null");
        if (customer.getAccountNumber() <= 0) throw new DaoException("Invalid account number");
        if (byId.containsKey(customer.getAccountNumber()))
            throw new DaoException("Customer already exists: " + customer.getAccountNumber());
        byId.put(customer.getAccountNumber(), copy(customer));
        return true;
    }

    @Override
    public Customer getCustomerByAccountNumber(int accountNumber) throws DaoException {
        Customer c = byId.get(accountNumber);
        return c == null ? null : copy(c);
    }

    @Override
    public boolean updateCustomer(Customer customer) throws DaoException {
        if (customer == null) throw new DaoException("Customer is null");
        int id = customer.getAccountNumber();
        if (!byId.containsKey(id)) return false;
        byId.put(id, copy(customer));
        return true;
    }

    @Override
    public boolean deleteCustomer(int accountNumber) throws DaoException {
        return byId.remove(accountNumber) != null;
    }

    @Override
    public List<Customer> getAllCustomers() throws DaoException {
        return byId.values().stream()
                .map(FakeCustomerDAO::copy)
                .sorted(Comparator.comparingInt(Customer::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> searchCustomersByName(String namePart) throws DaoException {
        if (namePart == null) namePart = "";
        final String needle = namePart.toLowerCase(Locale.ROOT);
        return byId.values().stream()
                .filter(c -> {
                    String n = c.getName();
                    return n != null && n.toLowerCase(Locale.ROOT).contains(needle);
                })
                .map(FakeCustomerDAO::copy)
                .sorted(Comparator.comparing(Customer::getName, Comparator.nullsLast(String::compareToIgnoreCase))
                        .thenComparingInt(Customer::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public boolean doesCustomerExist(int accountNumber) {
        return byId.containsKey(accountNumber);
    }

    // ---- helper ----
    private static Customer copy(Customer c) {
        Customer d = new Customer();
        d.setAccountNumber(c.getAccountNumber());
        d.setName(c.getName());
        d.setAddress(c.getAddress());
        d.setTelephone(c.getTelephone());
        d.setEmail(c.getEmail());
        return d;
    }
}
