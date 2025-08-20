package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.service.ItemService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/items/delete")
public class ItemDeleteServlet extends HttpServlet {
    private final ItemService service = new ItemService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sId = req.getParameter("itemId");
        if (sId == null) { resp.sendRedirect(req.getContextPath()+"/items"); return; }
        try {
            int id = Integer.parseInt(sId);
            boolean ok = service.deleteItem(id);
            resp.sendRedirect(req.getContextPath()+"/items" + (ok ? "?msg=deleted" : "?err=delete"));
        } catch (NumberFormatException | ServiceException e) {
            resp.sendRedirect(req.getContextPath()+"/items?err=invalid");
        }
    }
}
