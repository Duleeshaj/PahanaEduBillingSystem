package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;
import com.pahana.edu.util.CustomerRequestMapper;
import com.pahana.edu.exception.ServiceException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/updateCustomer")
public class CustomerUpdateServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(CustomerUpdateServlet.class.getName());
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Customer customer = CustomerRequestMapper.toCustomer(request);
            boolean success = customerService.updateCustomer(customer);

            if (success) {
                log.info("Successfully updated customer with accountNumber=" + customer.getAccountNumber());
                response.sendRedirect("success.jsp");
            } else {
                log.warning("Failed to update customer with accountNumber=" + customer.getAccountNumber());
                response.sendRedirect("error.jsp");
            }

        } catch (IllegalArgumentException e) {
            log.log(Level.WARNING, "Invalid input when updating customer", e);
            response.sendRedirect("error.jsp");

        } catch (ServiceException e) {
            log.log(Level.SEVERE, "Service layer error while updating customer", e);
            response.sendRedirect("error.jsp");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Unexpected error during customer update", e);
            response.sendRedirect("error.jsp");
        }
    }
}
