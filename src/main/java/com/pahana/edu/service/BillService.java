package com.pahana.edu.service;

import com.pahana.edu.dao.BillDAO;
import com.pahana.edu.dao.BillDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;

import java.util.List;

public class BillService {

    private final BillDAO billDAO;

    public BillService() {
        this.billDAO = new BillDAOImpl();
    }

    public boolean generateBill(int accountNumber, int unitsConsumed) throws ServiceException {
        double rate = 12.50;
        double total = unitsConsumed * rate;

        Bill bill = new Bill();
        bill.setAccountNumber(accountNumber);
        bill.setUnitsConsumed(unitsConsumed);
        bill.setUnitRate(rate);
        bill.setTotalAmount(total);

        try {
            return billDAO.addBill(bill);
        } catch (DaoException e) {
            throw new ServiceException("Error while generating bill", e);
        }
    }

    public List<Bill> getBillsByCustomer(int accountNumber) throws ServiceException {
        try {
            return billDAO.getBillsByCustomer(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while retrieving bills", e);
        }
    }
}
