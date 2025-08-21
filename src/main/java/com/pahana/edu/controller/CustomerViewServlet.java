package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/customers/view")
public class CustomerViewServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CustomerViewServlet.class.getName());
    private final CustomerService service = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sAcc = req.getParameter("accountNumber");
        if (sAcc == null) { resp.sendRedirect(req.getContextPath() + "/customers"); return; }
        try {
            int account = Integer.parseInt(sAcc);
            Customer c = service.getCustomerByAccountNumber(account);
            if (c == null) { resp.sendRedirect(req.getContextPath() + "/customers?err=notfound"); return; }
            req.setAttribute("customer", c);
            req.getRequestDispatcher("/customer-view.jsp").forward(req, resp);
        } catch (NumberFormatException | ServiceException | ServletException e) {
            log.log(Level.WARNING, "View load failed", e);
            resp.sendRedirect(req.getContextPath() + "/customers?err=invalid");
        }
    }
}
