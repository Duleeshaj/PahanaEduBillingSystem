package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/customers/edit")
public class CustomerEditServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CustomerEditServlet.class.getName());
    private final CustomerService service = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sAcc = req.getParameter("accountNumber");
        if (sAcc == null) { resp.sendRedirect(req.getContextPath() + "/customers"); return; }
        try {
            int account = Integer.parseInt(sAcc);
            Customer c = service.getCustomerByAccountNumber(account);
            if (c == null) { resp.sendRedirect(req.getContextPath() + "/customers?err=notfound"); return; }
            req.setAttribute("customer", c);
            req.getRequestDispatcher("/customer-edit.jsp").forward(req, resp);
        } catch (NumberFormatException | ServiceException | ServletException e) {
            log.log(Level.WARNING, "Edit load failed", e);
            resp.sendRedirect(req.getContextPath() + "/customers?err=invalid");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sAcc = req.getParameter("accountNumber");
        String name = trim(req.getParameter("name"));
        String address = trim(req.getParameter("address"));
        String tel = trim(req.getParameter("telephone"));
        String sUnits = trim(req.getParameter("unitsConsumed"));

        if (isBlank(sAcc) || isBlank(name) || isBlank(address) || isBlank(tel) || isBlank(sUnits)) {
            req.setAttribute("error", "All fields are required.");
            forwardBack(req, resp, "/customer-edit.jsp");
            return;
        }
        int account, units;
        try {
            account = Integer.parseInt(sAcc);
            units = Integer.parseInt(sUnits);
            if (units < 0) throw new NumberFormatException("Negative units");
        } catch (NumberFormatException ex) {
            req.setAttribute("error", "Account number and units must be valid non-negative integers.");
            forwardBack(req, resp, "/customer-edit.jsp");
            return;
        }
        if (!tel.matches("^[0-9+\\-() ]{7,20}$")) {
            req.setAttribute("error", "Telephone format is invalid.");
            forwardBack(req, resp, "/customer-edit.jsp");
            return;
        }

        Customer c = new Customer(account, name, address, tel, units);
        try {
            boolean ok = service.updateCustomer(c);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/customers?msg=updated");
            } else {
                req.setAttribute("error", "Update failed.");
                forwardBack(req, resp, "/customer-edit.jsp");
            }
        } catch (ServiceException e) {
            log.log(Level.SEVERE, "Update customer failed", e);
            req.setAttribute("error", "Internal error. Try again later.");
            forwardBack(req, resp, "/customer-edit.jsp");
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static void forwardBack(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        try { req.getRequestDispatcher(path).forward(req, resp); }
        catch (ServletException e) { resp.sendRedirect(req.getContextPath() + "/error.jsp"); }
    }
}
