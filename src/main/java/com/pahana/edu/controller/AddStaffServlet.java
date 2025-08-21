package com.pahana.edu.controller;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/staff/add")
public class AddStaffServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String ctx = req.getContextPath();

        String performedByRole = (String) session.getAttribute("role");
        // equalsIgnoreCase on a constant safely handles null; no separate null check needed
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            resp.sendRedirect(ctx + "/login");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            session.setAttribute("error", "Username and password are required.");
            resp.sendRedirect(ctx + "/admin/staff");
            return;
        }

        User u = new User();
        u.setUsername(username.trim());
        u.setPassword(password);
        u.setRole("STAFF");

        try {
            boolean ok = userService.addUser(u, performedByRole);
            session.setAttribute("message", ok ? "Staff member added." : "Failed to add staff.");
        } catch (ServiceException e) {
            session.setAttribute("error", e.getMessage());
        }

        resp.sendRedirect(ctx + "/admin/staff");
    }
}
