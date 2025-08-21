package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/bill/view")
public class BillViewServlet extends HttpServlet {
    private final BillService service = new BillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idStr = req.getParameter("billId");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/bills.jsp?err=Missing+bill+id");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException nfe) {
            resp.sendRedirect(req.getContextPath() + "/bills.jsp?err=Invalid+bill+id");
            return;
        }

        try {
            Bill bill = service.getBill(id);         // loads header
            if (bill == null) {
                resp.sendRedirect(req.getContextPath() + "/bills.jsp?err=Bill+not+found");
                return;
            }
            List<BillItem> items = service.getItems(id);
            bill.setItems(items);

            req.setAttribute("bill", bill);
            req.getRequestDispatcher("/bill-view.jsp").forward(req, resp);

        } catch (ServiceException se) {
            resp.sendRedirect(req.getContextPath() + "/bills.jsp?err=" + java.net.URLEncoder.encode("Unable to load bill", StandardCharsets.UTF_8));
        }
    }
}
