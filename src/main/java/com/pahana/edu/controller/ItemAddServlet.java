package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/items/add")
public class ItemAddServlet extends HttpServlet {
    private final ItemService service = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/item-add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name   = trim(req.getParameter("name"));
        String desc   = trim(req.getParameter("description"));
        String sPrice = trim(req.getParameter("price"));
        String sStock = trim(req.getParameter("stock"));

        try {
            BigDecimal price = new BigDecimal(sPrice);
            int stock = Integer.parseInt(sStock);
            Item item = new Item(name, desc, price, stock);

            int newId = service.addItem(item);
            if (newId > 0) {
                resp.sendRedirect(req.getContextPath() + "/items?msg=added");
                return;
            }
            req.setAttribute("error", "Failed to add item.");
            req.getRequestDispatcher("/item-add.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid price or stock value.");
            req.getRequestDispatcher("/item-add.jsp").forward(req, resp);
        } catch (ServiceException e) {
            req.setAttribute("error", "Server error while adding item.");
            req.getRequestDispatcher("/item-add.jsp").forward(req, resp);
        }
    }

    private static String trim(String s){ return s==null?null:s.trim(); }
}
