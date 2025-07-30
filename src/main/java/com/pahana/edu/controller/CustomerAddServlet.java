package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;
import com.pahana.edu.controller.util.CustomerRequestMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/addCustomer")
public class CustomerAddServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomerAddServlet.class);
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Customer customer = CustomerRequestMapper.toCustomer(request);
            boolean success = customerService.registerCustomer(customer);

            if (success) {
                response.sendRedirect("success.jsp");
            } else {
                log.warn("Customer registration failed for accountNumber={}", customer.getAccountNumber());
                response.sendRedirect("error.jsp");
            }

        } catch (IllegalArgumentException e) {
            log.error("Bad request data when adding customer", e);
            response.sendRedirect("error.jsp");
        } catch (ServiceException e) {
            log.error("Service error while adding customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}
