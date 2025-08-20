package com.pahana.edu.service;

import com.pahana.edu.dao.BillDAO;
import com.pahana.edu.dao.BillDAOImpl;
import com.pahana.edu.dao.ConfigDAO;
import com.pahana.edu.dao.ConfigDAOImpl;
import com.pahana.edu.dao.CustomerDAO;
import com.pahana.edu.dao.CustomerDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;

import java.util.List;

/**
 * Business logic for Billing.
 * - Validates inputs and preconditions
 * - Fetches pricing configuration (unit rate)
 * - Delegates persistence to DAO (which uses stored procedures)
 */
public class BillService {

    private final BillDAO billDAO;
    private final CustomerDAO customerDAO;
    private final ConfigDAO configDAO;

    public BillService() {
        this.billDAO = new BillDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
        this.configDAO = new ConfigDAOImpl();
    }

    /**
     * Generate a bill for a given customer and units.
     * Steps:
     *  1) Validate numbers
     *  2) Ensure customer exists
     *  3) Load current unit rate from config
     *  4) Compute total = units * rate
     *  5) Persist via DAO (SP: sp_addBill)
     */
    public boolean generateBill(int accountNumber, int unitsConsumed) throws ServiceException {
        if (accountNumber <= 0) {
            throw new ServiceException("Invalid account number");
        }
        if (unitsConsumed < 0) {
            throw new ServiceException("Units cannot be negative");
        }

        try {
            // Ensure customer exists
            boolean exists = customerDAO.doesCustomerExist(accountNumber);
            if (!exists) {
                throw new ServiceException("Customer " + accountNumber + " does not exist.");
            }

            // Get current unit rate from configuration
            double unitRate = configDAO.getUnitRate();
            if (unitRate < 0) {
                throw new ServiceException("Invalid unit rate in configuration");
            }

            // Compute total
            double total = unitsConsumed * unitRate;

            // Build Bill model
            Bill bill = new Bill();
            bill.setAccountNumber(accountNumber);
            bill.setUnitsConsumed(unitsConsumed);
            bill.setUnitRate(unitRate);
            bill.setTotalAmount(total);

            // Persist via DAO (stored procedure)
            return billDAO.addBill(bill);

        } catch (DaoException e) {
            throw new ServiceException("Error while generating bill", e);
        }
    }

    /** Get all bills for a customer (SP: sp_getBillsByCustomer). */
    public List<Bill> getBillsByCustomer(int accountNumber) throws ServiceException {
        if (accountNumber <= 0) {
            throw new ServiceException("Invalid account number");
        }
        try {
            return billDAO.getBillsByCustomer(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while retrieving bills for customer " + accountNumber, e);
        }
    }

    /** Get a single bill by its ID (SP: sp_getBillById). */
    public Bill getBillById(int billId) throws ServiceException {
        if (billId <= 0) {
            throw new ServiceException("Invalid bill id");
        }
        try {
            return billDAO.getBillById(billId);
        } catch (DaoException e) {
            throw new ServiceException("Error while retrieving bill " + billId, e);
        }
    }

    /** Get all bills (SP: sp_getAllBills). Useful for admin history screen. */
    public List<Bill> getAllBills() throws ServiceException {
        try {
            return billDAO.getAllBills();
        } catch (DaoException e) {
            throw new ServiceException("Error while retrieving all bills", e);
        }
    }
}
