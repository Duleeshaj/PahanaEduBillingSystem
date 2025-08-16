package com.pahana.edu.controller;

import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateUserStatus")
public class UserStatusServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        String performedByRole = (String) request.getSession().getAttribute("role");

        try {
            boolean success = userService.setUserActiveStatus(userId, isActive, performedByRole);
            if (success) {
                request.setAttribute("message", "User status updated successfully!");
            } else {
                request.setAttribute("message", "Failed to update user status!");
            }
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("userManagement.jsp").forward(request, response);
    }
}
