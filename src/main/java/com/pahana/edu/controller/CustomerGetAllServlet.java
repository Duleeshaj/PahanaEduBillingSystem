package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/getAllCustomers")
public class CustomerGetAllServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomerGetAllServlet.class);
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("customerList.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Failed to retrieve all customers", e);
            response.sendRedirect("error.jsp");
        }
    }
}
