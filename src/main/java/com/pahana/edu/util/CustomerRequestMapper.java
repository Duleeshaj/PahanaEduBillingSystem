package com.pahana.edu.util;

import com.pahana.edu.model.Customer;

import javax.servlet.http.HttpServletRequest;

public final class CustomerRequestMapper {

    private CustomerRequestMapper() {
        // prevent instantiation
    }

    /**Maps request params to a Customer.
     * Accepts either "unitsConsumed" (preferred) or "units" for the units field.
     * Required params: accountNumber, name, address, telephone.*/
    public static Customer toCustomer(HttpServletRequest request) throws IllegalArgumentException {
        try {
            String accStr = trim(request.getParameter("accountNumber"));
            String name = trim(request.getParameter("name"));
            String address = trim(request.getParameter("address"));
            String telephone = trim(request.getParameter("telephone"));

            // Prefer "unitsConsumed"; fallback to "units"
            String unitsStr = trim(request.getParameter("unitsConsumed"));
            if (unitsStr == null) {
                unitsStr = trim(request.getParameter("units"));
            }

            if (isBlank(accStr)) {
                throw new IllegalArgumentException("Missing required field: accountNumber");
            }
            if (isBlank(name) || isBlank(address) || isBlank(telephone)) {
                throw new IllegalArgumentException("Missing required fields: name/address/telephone");
            }

            int accountNumber = Integer.parseInt(accStr);
            int units = (isBlank(unitsStr)) ? 0 : Integer.parseInt(unitsStr);

            return new Customer(accountNumber, name, address, telephone, units);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value in request parameters", e);
        }
    }

    // --- helpers ---
    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.isEmpty(); }
}
