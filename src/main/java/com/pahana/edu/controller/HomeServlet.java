package com.pahana.edu.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        Object roleObj = session.getAttribute("role");
        String role = roleObj == null ? "" : roleObj.toString();

        if ("ADMIN".equalsIgnoreCase(role)) {
            resp.sendRedirect(req.getContextPath() + "/admin-dashboard.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/staff-dashboard.jsp");
        }
    }
}
