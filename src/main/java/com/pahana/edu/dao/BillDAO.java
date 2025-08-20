package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Bill;

import java.util.List;

public interface BillDAO {
    boolean addBill(Bill bill) throws DaoException;
    List<Bill> getBillsByCustomer(int accountNumber) throws DaoException;
    Bill getBillById(int billId) throws DaoException;
    List<Bill> getAllBills() throws DaoException;
}
