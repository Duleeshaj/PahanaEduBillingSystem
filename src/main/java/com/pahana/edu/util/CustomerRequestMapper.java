package com.pahana.edu.util;

import com.pahana.edu.model.Customer;

import javax.servlet.http.HttpServletRequest;

public final class CustomerRequestMapper {

    private CustomerRequestMapper() {
        // prevent instantiation
    }

    public static Customer toCustomer(HttpServletRequest request) throws IllegalArgumentException {
        try {
            int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            int units = Integer.parseInt(request.getParameter("units"));

            return new Customer(accountNumber, name, address, telephone, units);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value in request parameters", e);
        }
    }
}
