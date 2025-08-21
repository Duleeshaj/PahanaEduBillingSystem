package com.pahana.edu.controller;

import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/auditLogs")
public class AuditLogServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String performedByRole = (String) request.getSession().getAttribute("role");

        try {
            List<String> logs = userService.viewAuditLogs(performedByRole);
            request.setAttribute("logs", logs);
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("auditLogs.jsp").forward(request, response);
    }
}
