package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/searchCustomer")
public class CustomerSearchServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(CustomerSearchServlet.class.getName());
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String param = request.getParameter("accountNumber");
            int accountNumber = Integer.parseInt(param);

            Customer customer = customerService
                    .getCustomerByAccountNumber(accountNumber)
                    .orElse(null);

            if (customer != null) {
                request.setAttribute("customer", customer);
                try {
                    request.getRequestDispatcher("customerDetails.jsp").forward(request, response);
                } catch (ServletException se) {
                    log.log(Level.SEVERE, "Servlet exception while forwarding to customerDetails.jsp", se);
                    response.sendRedirect("error.jsp");
                }
            } else {
                response.sendRedirect("notfound.jsp");
            }

        } catch (NumberFormatException e) {
            log.log(Level.WARNING, "Invalid account number format", e);
            response.sendRedirect("error.jsp");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error searching customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}
