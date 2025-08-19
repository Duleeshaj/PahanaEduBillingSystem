package com.pahana.edu.controller;

import com.pahana.edu.service.CustomerService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/deleteCustomer")
public class CustomerDeleteServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(CustomerDeleteServlet.class.getName());
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String accountParam = request.getParameter("accountNumber");
            int accountNumber = Integer.parseInt(accountParam);

            boolean success = customerService.deleteCustomer(accountNumber);

            if (success) {
                log.info("Successfully deleted customer with accountNumber=" + accountNumber);
                response.sendRedirect("success.jsp");
            } else {
                log.warning("Failed to delete customer with accountNumber=" + accountNumber);
                response.sendRedirect("error.jsp");
            }

        } catch (NumberFormatException e) {
            log.log(Level.WARNING, "Invalid account number format", e);
            response.sendRedirect("error.jsp");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Unexpected error occurred while deleting customer", e);
            response.sendRedirect("error.jsp");
        }
    }
}
