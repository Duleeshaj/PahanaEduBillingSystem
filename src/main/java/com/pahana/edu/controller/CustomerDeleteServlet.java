package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.service.CustomerService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/customers/delete")
public class CustomerDeleteServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CustomerDeleteServlet.class.getName());
    private final CustomerService service = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sAcc = req.getParameter("accountNumber");
        if (sAcc == null) { resp.sendRedirect(req.getContextPath() + "/customers"); return; }
        try {
            int account = Integer.parseInt(sAcc);
            boolean ok = service.deleteCustomer(account);
            resp.sendRedirect(req.getContextPath() + "/customers" + (ok ? "?msg=deleted" : "?err=delete"));
        } catch (NumberFormatException | ServiceException e) {
            log.log(Level.WARNING, "Delete failed", e);
            resp.sendRedirect(req.getContextPath() + "/customers?err=invalid");
        }
    }
}
