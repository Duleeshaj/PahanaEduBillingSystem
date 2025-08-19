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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Single servlet handles both "/customerList" and "/getAllCustomers"
 * to eliminate duplicate code across two similar servlets.*/
@WebServlet(urlPatterns = {"/customerList", "/getAllCustomers"})
public class CustomerListServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(CustomerListServlet.class.getName());
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            request.setAttribute("customers", customers);

            // Forward to the list view; catch ServletException locally so the method signature stays clean.
            try {
                request.getRequestDispatcher("customerList.jsp").forward(request, response);
            } catch (ServletException se) {
                log.log(Level.SEVERE, "Servlet exception while forwarding to customerList.jsp", se);
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error loading customer list", e);
            response.sendRedirect("error.jsp");
        }
    }
}
