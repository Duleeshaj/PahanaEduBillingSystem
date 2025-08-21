package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.BillItemRequest;

import java.time.LocalDate;
import java.util.List;

public interface BillDAO {
    /** Creates a bill with items, updates stock atomically. Returns new bill_id. */
    int createBill(int accountNumber, List<BillItemRequest> items) throws DaoException;

    Bill getBillById(int billId) throws DaoException;
    List<Bill> getBillsByCustomer(int accountNumber) throws DaoException;
    List<Bill> getAllBills() throws DaoException;
    List<BillItem> getBillItems(int billId) throws DaoException;
    List<Bill> getBillsByDateRange(LocalDate from, LocalDate to) throws DaoException;

}
