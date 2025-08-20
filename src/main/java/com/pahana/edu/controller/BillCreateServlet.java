package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="BillCreateServlet", urlPatterns={"/billing","/billing/create"})
public class BillCreateServlet extends HttpServlet {

    private final BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Show the create-bill page
        req.getRequestDispatcher("/billing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String accStr = req.getParameter("accountNumber");
        String unitsStr = req.getParameter("unitsConsumed");

        if (accStr == null || unitsStr == null || accStr.trim().isEmpty() || unitsStr.trim().isEmpty()) {
            req.setAttribute("error", "Account number and units are required.");
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
            return;
        }

        try {
            int accountNumber = Integer.parseInt(accStr.trim());
            int units = Integer.parseInt(unitsStr.trim());
            if (units < 0) {
                req.setAttribute("error", "Units cannot be negative.");
                req.getRequestDispatcher("/billing.jsp").forward(req, resp);
                return;
            }

            boolean ok = billService.generateBill(accountNumber, units);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/bills?q=" + accountNumber + "&msg=created");
            } else {
                req.setAttribute("error", "Failed to create bill.");
                req.getRequestDispatcher("/billing.jsp").forward(req, resp);
            }
        } catch (NumberFormatException nfe) {
            req.setAttribute("error", "Account number and units must be numbers.");
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
        } catch (ServiceException se) {
            req.setAttribute("error", se.getMessage());
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
        }
    }
}
