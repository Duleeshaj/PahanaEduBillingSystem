package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;
import com.pahana.edu.util.CustomerRequestMapper;
import com.pahana.edu.exception.ServiceException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/updateCustomer")
public class CustomerUpdateServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomerUpdateServlet.class);
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Customer customer = CustomerRequestMapper.toCustomer(request);
            boolean success = customerService.updateCustomer(customer);

            if (success) {
                log.info("Successfully updated customer with accountNumber={}", customer.getAccountNumber());
                response.sendRedirect("success.jsp");
            } else {
                log.warn("Failed to update customer with accountNumber={}", customer.getAccountNumber());
                response.sendRedirect("error.jsp");
            }

        } catch (IllegalArgumentException e) {
            log.error("Invalid input when updating customer", e);
            response.sendRedirect("error.jsp");

        } catch (ServiceException e) {
            log.error("Service layer error while updating customer", e);
            response.sendRedirect("error.jsp");

        } catch (Exception e) {
            log.error("Unexpected error during customer update", e);
            response.sendRedirect("error.jsp");
        }
    }
}
