package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;
import com.pahana.edu.util.CustomerRequestMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/addCustomer")
public class CustomerAddServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(CustomerAddServlet.class.getName());
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Optional but recommended for form posts
        request.setCharacterEncoding("UTF-8");

        try {
            // Convert request data into Customer object
            Customer customer = CustomerRequestMapper.toCustomer(request);

            // Call service to register customer
            boolean success = customerService.registerCustomer(customer);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/success.jsp");
            } else {
                log.warning("Customer registration failed for accountNumber=" + customer.getAccountNumber());
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            }

        } catch (IllegalArgumentException e) {
            log.severe("Bad request data when adding customer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        } catch (ServiceException e) {
            log.severe("Service error while adding customer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
