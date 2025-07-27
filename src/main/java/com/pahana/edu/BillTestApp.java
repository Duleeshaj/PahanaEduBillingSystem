package com.pahana.edu;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.BillService;
import com.pahana.edu.service.CustomerService;

import java.util.List;
import java.util.Optional;

public class BillTestApp {

    public static void main(String[] args) {
        BillService billService = new BillService();
        CustomerService customerService = new CustomerService();

        int testAccountNumber = 101; // keep as-is
        int testUnits = 30;

        // Ensure customer exists
        try {
            Optional<Customer> c = customerService.getCustomerByAccountNumber(testAccountNumber);
            if (c.isEmpty()) {
                Customer nc = new Customer(testAccountNumber, "Test Customer", "Colombo", "0710000000", testUnits);
                boolean created = customerService.registerCustomer(nc);
                System.out.println("Created test customer 5001: " + created);
            }
        } catch (ServiceException e) {
            System.out.println("Failed to ensure customer exists: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // TEST 1
        try {
            boolean success = billService.generateBill(testAccountNumber, testUnits);
            System.out.println("✅ TEST 1 - Generate Bill: " + (success ? "PASSED" : "FAILED"));
        } catch (ServiceException e) {
            System.out.println("❌ TEST 1 - Generate Bill: EXCEPTION");
            e.printStackTrace();
        }

        // TEST 2
        try {
            List<Bill> bills = billService.getBillsByCustomer(testAccountNumber);
            System.out.println("✅ TEST 2 - Get Bills By Customer: Found " + bills.size() + " record(s)");
            for (Bill b : bills) {
                System.out.printf("→ BillID: %d | Units: %d | Rate: %.2f | Total: %.2f | Date: %s%n",
                        b.getBillId(), b.getUnitsConsumed(), b.getUnitRate(), b.getTotalAmount(), b.getBillDate());
            }
        } catch (ServiceException e) {
            System.out.println("❌ TEST 2 - Get Bills By Customer: EXCEPTION");
            e.printStackTrace();
        }
    }
}
