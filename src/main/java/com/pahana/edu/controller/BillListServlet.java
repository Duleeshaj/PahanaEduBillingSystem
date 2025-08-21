package com.pahana.edu.controller;

import com.pahana.edu.model.Bill;
import com.pahana.edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/bills")
public class BillListServlet extends HttpServlet {
    private final BillService service = new BillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String fromStr = req.getParameter("from");
        String toStr   = req.getParameter("to");

        try {
            List<Bill> bills;
            if (fromStr != null && !fromStr.isEmpty() && toStr != null && !toStr.isEmpty()) {
                LocalDate from = LocalDate.parse(fromStr);
                LocalDate to   = LocalDate.parse(toStr);
                bills = service.listByDateRange(from, to);
                req.setAttribute("from", fromStr);
                req.setAttribute("to", toStr);
            } else {
                bills = service.listAll();
            }
            req.setAttribute("bills", bills);
            req.getRequestDispatcher("/bills.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Unable to load bills.");
            req.getRequestDispatcher("/bills.jsp").forward(req, resp);
        }
    }
}
