package com.pahana.edu.service;

import com.pahana.edu.dao.BillDAO;
import com.pahana.edu.dao.BillDAOImpl;
import com.pahana.edu.dao.CustomerDAO;
import com.pahana.edu.dao.CustomerDAOImpl;
import com.pahana.edu.exception.DaoException;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.BillItemRequest;
import com.pahana.edu.model.Customer;

import java.util.List;

public class BillService {

    private final BillDAO billDAO = new BillDAOImpl();
    private final CustomerDAO customerDAO = new CustomerDAOImpl();

    /** emailOrNull can be null; accountNumberOrZero can be 0. One of them must be provided. */
    public int generateBill(Integer accountNumberOrNull, String emailOrNull, List<BillItemRequest> items)
            throws ServiceException {
        try {
            int acc;
            if (accountNumberOrNull != null && accountNumberOrNull > 0) {
                acc = accountNumberOrNull;
            } else if (emailOrNull != null && !emailOrNull.trim().isEmpty()) {
                Customer c = findByEmail(emailOrNull.trim());
                if (c == null) throw new ServiceException("Customer not found for email");
                acc = c.getAccountNumber();
            } else {
                throw new ServiceException("Provide account number or email");
            }

            if (items == null || items.isEmpty()) throw new ServiceException("No items selected");

            return billDAO.createBill(acc, items);

        } catch (DaoException e) {
            throw new ServiceException("Failed to generate bill", e);
        }
    }

    public Bill getBill(int billId) throws ServiceException {
        try { return billDAO.getBillById(billId); }
        catch (DaoException e) { throw new ServiceException("Failed to get bill", e); }
    }

    public List<Bill> listAll() throws ServiceException {
        try { return billDAO.getAllBills(); }
        catch (DaoException e) { throw new ServiceException("Failed to list bills", e); }
    }

    public List<Bill> listByCustomer(int accountNumber) throws ServiceException {
        try { return billDAO.getBillsByCustomer(accountNumber); }
        catch (DaoException e) { throw new ServiceException("Failed to list customer bills", e); }
    }

    public List<BillItem> getItems(int billId) throws ServiceException {
        try { return billDAO.getBillItems(billId); }
        catch (DaoException e) { throw new ServiceException("Failed to get bill items", e); }
    }

    private Customer findByEmail(String email) throws DaoException {
        // quick helper since your DAO doesn’t have it yet (add it if needed)
        // simple direct SQL fallback:
        return customerDAO.searchCustomersByName("")  // quick reuse, but better: implement getByEmail in DAO
                .stream().filter(c -> email.equalsIgnoreCase(c.getEmail())).findFirst().orElse(null);
    }

    public List<Bill> listByDateRange(java.time.LocalDate from, java.time.LocalDate to) throws ServiceException {
        try { return billDAO.getBillsByDateRange(from, to); }
        catch (DaoException e) { throw new ServiceException("Failed to list by date", e); }
    }
}
