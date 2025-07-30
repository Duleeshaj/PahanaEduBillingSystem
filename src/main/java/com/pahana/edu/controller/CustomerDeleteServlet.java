package com.pahana.edu.controller;

import com.pahana.edu.service.CustomerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/deleteCustomer")
public class CustomerDeleteServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomerDeleteServlet.class);
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
            boolean success = customerService.deleteCustomer(accountNumber);

            if (success) {
                log.info("Successfully deleted customer with accountNumber={}", accountNumber);
                response.sendRedirect("success.jsp");
            } else {
                log.warn("Failed to delete customer with accountNumber={}", accountNumber);
                response.sendRedirect("error.jsp");
            }

        } catch (NumberFormatException e) {
            log.error("Invalid account number format", e);
            response.sendRedirect("error.jsp");

        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}
