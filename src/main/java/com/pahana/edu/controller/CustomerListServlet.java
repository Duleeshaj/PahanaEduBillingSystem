package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/customers")
public class CustomerListServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CustomerListServlet.class.getName());
    private final CustomerService service = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String q = req.getParameter("q"); // optional name search
        try {
            List<Customer> customers = (q == null || q.trim().isEmpty())
                    ? service.getAllCustomers()
                    : service.searchCustomersByName(q.trim());
            req.setAttribute("customers", customers);
            req.setAttribute("q", q);
            req.getRequestDispatcher("/customers.jsp").forward(req, resp);
        } catch (ServiceException | ServletException e) {
            log.log(Level.SEVERE, "List customers failed", e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
