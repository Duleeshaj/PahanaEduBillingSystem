package com.pahana.edu.service;

import com.pahana.edu.dao.BillDAO;
import com.pahana.edu.dao.BillDAOImpl;
import com.pahana.edu.dao.CustomerDAO;
import com.pahana.edu.dao.CustomerDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;

import java.util.List;

public class BillService {

    private final BillDAO billDAO;
    private final CustomerDAO customerDAO;

    public BillService() {
        this.billDAO = new BillDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
    }

    public boolean generateBill(int accountNumber, int unitsConsumed) throws ServiceException {
        try {
            // ✅ Check if customer exists before generating bill
            if (!customerDAO.doesCustomerExist(accountNumber)) {
                throw new ServiceException("Cannot generate bill: Customer with account number " + accountNumber + " does not exist.");
            }

            double rate = 12.50;
            double total = unitsConsumed * rate;

            Bill bill = new Bill();
            bill.setAccountNumber(accountNumber);
            bill.setUnitsConsumed(unitsConsumed);
            bill.setUnitRate(rate);
            bill.setTotalAmount(total);

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
