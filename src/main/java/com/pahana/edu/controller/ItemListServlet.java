package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/items")
public class ItemListServlet extends HttpServlet {
    private final ItemService service = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String q = req.getParameter("q");
        try {
            List<Item> items = (q == null || q.trim().isEmpty())
                    ? service.getAllItems()
                    : service.searchItemsByName(q.trim());
            req.setAttribute("items", items);
            req.setAttribute("q", q);
            req.getRequestDispatcher("/items.jsp").forward(req, resp);
        } catch (ServiceException | ServletException e) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
