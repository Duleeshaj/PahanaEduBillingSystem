package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.BillItemRequest;
import com.pahana.edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/billing/create")
public class BillCreateServlet extends HttpServlet {
    private final BillService billService = new BillService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 1) Collect customer (email only; acc no removed per your new spec)
        final String email = trim(req.getParameter("email"));

        // 2) Collect line items: arrays itemId[], qty[]
        String[] itemIds = req.getParameterValues("itemId");
        String[] qtys    = req.getParameterValues("qty");

        // quick sanity checks
        if (email == null || email.isEmpty()) {
            req.setAttribute("error", "Customer email is required.");
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
            return;
        }
        if (itemIds == null || qtys == null || itemIds.length == 0 || qtys.length == 0) {
            req.setAttribute("error", "Please add at least one item.");
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
            return;
        }
        if (itemIds.length != qtys.length) {
            req.setAttribute("error", "Item list is malformed.");
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
            return;
        }

        // 3) Build requests
        List<BillItemRequest> items = new ArrayList<>();
        try {
            for (int i = 0; i < itemIds.length; i++) {
                int itemId = Integer.parseInt(itemIds[i].trim());
                int qty    = Integer.parseInt(qtys[i].trim());
                if (qty <= 0) continue; // ignore zero/negative
                BillItemRequest r = new BillItemRequest();
                r.setItemId(itemId);
                r.setQty(qty);
                items.add(r);
            }
            if (items.isEmpty()) {
                req.setAttribute("error", "All quantities are zero. Add at least one item.");
                req.getRequestDispatcher("/billing.jsp").forward(req, resp);
                return;
            }

            // 4) Generate bill (email -> resolve customer; stock + totals handled in DAO)
            int billId = billService.generateBill(null, email, items);

            // 5) Redirect to the **viewer** with correct param name
            resp.sendRedirect(req.getContextPath() + "/bill/view?billId=" + billId);

        } catch (NumberFormatException nfe) {
            req.setAttribute("error", "Invalid item or quantity.");
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
        } catch (ServiceException se) {
            req.setAttribute("error", se.getMessage());
            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
}
