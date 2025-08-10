package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/searchCustomer")
public class CustomerSearchServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomerSearchServlet.class);
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
            Customer customer = customerService.getCustomerByAccountNumber(accountNumber).orElse(null);

            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("customerDetails.jsp").forward(request, response);
            } else {
                response.sendRedirect("notfound.jsp");
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid account number format", e);
            response.sendRedirect("error.jsp");
        } catch (Exception e) {
            log.error("Error searching customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}