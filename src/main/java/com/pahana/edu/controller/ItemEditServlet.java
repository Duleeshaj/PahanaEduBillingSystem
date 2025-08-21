package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/items/edit")
public class ItemEditServlet extends HttpServlet {
    private final ItemService service = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sId = req.getParameter("itemId");
        if (sId == null) { resp.sendRedirect(req.getContextPath() + "/items"); return; }
        try {
            int id = Integer.parseInt(sId);
            Item item = service.getItemById(id);
            if (item == null) { resp.sendRedirect(req.getContextPath()+"/items?err=notfound"); return; }
            req.setAttribute("item", item);
            req.getRequestDispatcher("/item-edit.jsp").forward(req, resp);
        } catch (NumberFormatException | ServiceException | ServletException e) {
            resp.sendRedirect(req.getContextPath()+"/items?err=invalid");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sId = req.getParameter("itemId");
        String name = req.getParameter("name");
        String desc = req.getParameter("description");
        String sPrice = req.getParameter("price");
        String sStock = req.getParameter("stock");

        try {
            int id = Integer.parseInt(sId);
            BigDecimal price = new BigDecimal(sPrice);
            int stock = Integer.parseInt(sStock);
            Item item = new Item(id, name, desc, price, stock);
            boolean ok = service.updateItem(item);
            resp.sendRedirect(req.getContextPath()+"/items" + (ok ? "?msg=updated" : "?err=update"));
        } catch (NumberFormatException | ServiceException e) {
            req.setAttribute("error", "Invalid data or server error.");
            try { req.getRequestDispatcher("/item-edit.jsp").forward(req, resp); }
            catch (ServletException se) { resp.sendRedirect(req.getContextPath()+"/error.jsp"); }
        }
    }
}
