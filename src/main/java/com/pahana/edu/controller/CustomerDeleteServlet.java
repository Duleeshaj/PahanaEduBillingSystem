package com.pahana.edu.controller;

import com.pahana.edu.controller.util.CustomerRequestMapper;
import com.pahana.edu.service.CustomerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/deleteCustomer")
public class CustomerDeleteServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CustomerDeleteServlet.class.getName());
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
            boolean success = customerService.deleteCustomer(accountNumber);

            if (success) {
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid account number format", e);
            response.sendRedirect("error.jsp");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}
