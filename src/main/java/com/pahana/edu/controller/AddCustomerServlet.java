package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            int units = Integer.parseInt(request.getParameter("units"));

            Customer customer = new Customer(accountNumber, name, address, telephone, units);
            boolean success = customerService.registerCustomer(customer);

            if (success) {
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
