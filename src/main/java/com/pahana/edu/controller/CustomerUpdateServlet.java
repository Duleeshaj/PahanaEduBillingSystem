package com.pahana.edu.controller;

import com.pahana.edu.controller.util.CustomerRequestMapper;
import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/updateCustomer")
public class CustomerUpdateServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomerUpdateServlet.class);
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Customer customer = CustomerRequestMapper.toCustomer(request);
            boolean success = customerService.updateCustomer(customer);

            if (success) {
                response.sendRedirect("success.jsp");
            } else {
                log.warn("Customer update failed for accountNumber={}", customer.getAccountNumber());
                response.sendRedirect("error.jsp");
            }

        } catch (IllegalArgumentException e) {
            log.error("Bad request data when updating customer", e);
            response.sendRedirect("error.jsp");
        } catch (ServiceException e) {
            log.error("Service error while updating customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}
