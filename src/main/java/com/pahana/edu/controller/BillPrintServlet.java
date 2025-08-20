package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;
import com.pahana.edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="BillPrintServlet", urlPatterns={"/bill/print"})
public class BillPrintServlet extends HttpServlet {
    private final BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("billId");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/bills?err=invalid");
            return;
        }
        try {
            int billId = Integer.parseInt(idStr.trim());
            Bill bill = billService.getBillById(billId);
            if (bill == null) {
                resp.sendRedirect(req.getContextPath() + "/bills?err=notfound");
                return;
            }
            req.setAttribute("bill", bill);
            req.getRequestDispatcher("/bill-print.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/bills?err=invalid");
        } catch (ServiceException e) {
            resp.sendRedirect(req.getContextPath() + "/bills?err=internal");
        }
    }
}
