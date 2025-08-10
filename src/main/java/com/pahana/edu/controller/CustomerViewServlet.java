package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/viewCustomer")
public class CustomerViewServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomerViewServlet.class);
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
            Customer customer = customerService.getCustomerByAccountNumber(accountNumber).orElse(null);

            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("customerView.jsp").forward(request, response);
            } else {
                response.sendRedirect("notfound.jsp");
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid account number format", e);
            response.sendRedirect("error.jsp");
        } catch (Exception e) {
            log.error("Error viewing customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}