package com.pahana.edu.util;

import com.pahana.edu.model.Customer;

import javax.servlet.http.HttpServletRequest;

public final class CustomerRequestMapper {

    private CustomerRequestMapper() { }

    /**
     * Maps request params to a Customer.
     * Required params: accountNumber, name, address, telephone, email.
     * Accepts "email" (preferred) or "mail" as a fallback.
     */
    public static Customer toCustomer(HttpServletRequest request) throws IllegalArgumentException {
        String accStr    = trim(request.getParameter("accountNumber"));
        String name      = trim(request.getParameter("name"));
        String address   = trim(request.getParameter("address"));
        String telephone = trim(request.getParameter("telephone"));
        String email     = trim(request.getParameter("email"));
        if (email == null) email = trim(request.getParameter("mail"));

        if (isBlank(accStr)) {
            throw new IllegalArgumentException("Missing required field: accountNumber");
        }
        if (isBlank(name) || isBlank(address) || isBlank(telephone) || isBlank(email)) {
            throw new IllegalArgumentException("Missing required fields: name/address/telephone/email");
        }

        try {
            int accountNumber = Integer.parseInt(accStr);

            Customer c = new Customer();
            c.setAccountNumber(accountNumber);
            c.setName(name);
            c.setAddress(address);
            c.setTelephone(telephone);
            c.setEmail(email);
            return c;

        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid account number", nfe);
        }
    }

    // --- helpers ---
    private static String trim(String s) { return (s == null) ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
