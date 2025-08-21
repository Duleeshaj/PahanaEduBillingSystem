package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="CustomerAddServlet", urlPatterns={"/customers/add"})
public class CustomerAddServlet extends HttpServlet {
    private final CustomerService service = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/customer-add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String accStr = req.getParameter("accountNumber");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");

        if (isBlank(accStr) || isBlank(name) || isBlank(address) || isBlank(telephone) || isBlank(email)) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/customer-add.jsp").forward(req, resp);
            return;
        }

        try {
            int accountNumber = Integer.parseInt(accStr.trim());
            Customer c = new Customer(accountNumber, name.trim(), address.trim(), telephone.trim(), email.trim());
            boolean ok = service.addCustomer(c);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/customers?msg=added");
            } else {
                req.setAttribute("error", "Failed to add customer.");
                req.getRequestDispatcher("/customer-add.jsp").forward(req, resp);
            }
        } catch (NumberFormatException nfe) {
            req.setAttribute("error", "Account number must be a number.");
            req.getRequestDispatcher("/customer-add.jsp").forward(req, resp);
        } catch (ServiceException se) {
            req.setAttribute("error", se.getMessage());
            req.getRequestDispatcher("/customer-add.jsp").forward(req, resp);
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private static void forwardBack(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        try { req.getRequestDispatcher(path).forward(req, resp); }
        catch (ServletException e) { resp.sendRedirect(req.getContextPath() + "/error.jsp"); }
    }
}
