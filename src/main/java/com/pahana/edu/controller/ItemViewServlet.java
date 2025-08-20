package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/items/view")
public class ItemViewServlet extends HttpServlet {
    private final ItemService service = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sId = req.getParameter("itemId");
        if (sId == null) { resp.sendRedirect(req.getContextPath()+"/items"); return; }
        try {
            int id = Integer.parseInt(sId);
            Item item = service.getItemById(id);
            if (item == null) { resp.sendRedirect(req.getContextPath()+"/items?err=notfound"); return; }
            req.setAttribute("item", item);
            req.getRequestDispatcher("/item-view.jsp").forward(req, resp);
        } catch (NumberFormatException | ServiceException | ServletException e) {
            resp.sendRedirect(req.getContextPath()+"/items?err=invalid");
        }
    }
}
