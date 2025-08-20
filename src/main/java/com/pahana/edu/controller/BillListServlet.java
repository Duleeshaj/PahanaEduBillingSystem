package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;
import com.pahana.edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name="BillListServlet", urlPatterns={"/bills"})
public class BillListServlet extends HttpServlet {

    private final BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q"); // account number filter (optional)
        List<Bill> bills;

        try {
            if (q != null && !q.trim().isEmpty()) {
                int acc = Integer.parseInt(q.trim());
                bills = billService.getBillsByCustomer(acc);
                req.setAttribute("q", q);
            } else {
                // If you already have billService.getAllBills(), use it. If not, show empty with hint.
                bills = Collections.emptyList();
                req.setAttribute("hint", "Enter an Account # to view bill history.");
            }
        } catch (NumberFormatException nfe) {
            bills = Collections.emptyList();
            req.setAttribute("error", "Account number must be a number.");
        } catch (ServiceException se) {
            bills = Collections.emptyList();
            req.setAttribute("error", se.getMessage());
        }

        req.setAttribute("bills", bills);
        req.getRequestDispatcher("/bills.jsp").forward(req, resp);
    }
}
