package com.pahana.edu.controller;

import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/staff/toggle")
public class ToggleStaffStatusServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String ctx = req.getContextPath();

        String performedByRole = (String) session.getAttribute("role");
        if (!"ADMIN".equalsIgnoreCase(performedByRole)) {
            resp.sendRedirect(ctx + "/login");
            return;
        }

        int userId = Integer.parseInt(req.getParameter("userId"));
        boolean active = "1".equals(req.getParameter("active"));

        try {
            boolean ok = userService.setUserActiveStatus(userId, active, performedByRole);
            session.setAttribute("message", ok ? "Status updated." : "No change.");
        } catch (ServiceException e) {
            session.setAttribute("error", e.getMessage());
        }

        resp.sendRedirect(ctx + "/admin/staff");
    }
}
