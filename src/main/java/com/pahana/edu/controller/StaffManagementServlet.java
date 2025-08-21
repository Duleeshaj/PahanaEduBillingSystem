package com.pahana.edu.controller;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/staff")
public class StaffManagementServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        String role = (session == null) ? null : (String) session.getAttribute("role");
        if (!"ADMIN".equalsIgnoreCase(role)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Flash messages (moved from session to request)
        Object msg = session.getAttribute("message");
        if (msg != null) { req.setAttribute("message", msg); session.removeAttribute("message"); }
        Object err = session.getAttribute("error");
        if (err != null) { req.setAttribute("error", err); session.removeAttribute("error"); }

        try {
            List<User> staff = userService.listStaff(role); // role == ADMIN here
            req.setAttribute("staff", staff);
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher("/staff-management.jsp").forward(req, resp);
    }
}
