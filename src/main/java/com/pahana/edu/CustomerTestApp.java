package com.pahana.edu;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerTestApp {

    public static void main(String[] args) {

        CustomerService service = new CustomerService();

        try {
            // 1. ADD customer
            Customer newCustomer = new Customer(5001, "Nimal Perera", "Colombo 10", "0711234567", 150);
            boolean isAdded = service.registerCustomer(newCustomer);
            System.out.println("Customer added: " + isAdded);

            // 2. GET customer
            Optional<Customer> retrieved = service.getCustomerByAccountNumber(5001);
            if (retrieved.isPresent()) {
                Customer c = retrieved.get();
                System.out.println("Fetched Customer: " + c.getName() + ", " + c.getUnitsConsumed() + " units");
            } else {
                System.out.println("Customer not found.");
            }

            // 3. UPDATE customer
            if (retrieved.isPresent()) {
                Customer c = retrieved.get();
                c.setUnitsConsumed(200);
                boolean isUpdated = service.updateCustomer(c);
                System.out.println("Customer updated: " + isUpdated);
            }

            // 4. GET ALL customers
            List<Customer> customers = service.getAllCustomers();
            System.out.println("All Customers:");
            for (Customer c : customers) {
                System.out.println("- " + c.getAccountNumber() + ": " + c.getName());
            }

            // 5. DELETE customer
            boolean isDeleted = service.deleteCustomer(5001);
            System.out.println("Customer deleted: " + isDeleted);

            // 6. Confirm Deletion
            Optional<Customer> checkAgain = service.getCustomerByAccountNumber(5001);
            System.out.println("Exists after delete? " + checkAgain.isPresent());

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
