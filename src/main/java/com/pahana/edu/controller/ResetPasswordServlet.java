package com.pahana.edu.controller;

import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String newPassword = request.getParameter("newPassword");
        String performedByRole = (String) request.getSession().getAttribute("role");

        try {
            boolean success = userService.resetPassword(userId, newPassword, performedByRole);
            if (success) {
                request.setAttribute("message", "Password reset successfully!");
            } else {
                request.setAttribute("message", "Failed to reset password!");
            }
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("userManagement.jsp").forward(request, response);
    }
}
