package com.pahana.edu.controller;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Added ServletException to method signature

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.authenticateUser(username, password);

            if (user != null && user.isActive()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("loggedUser", user);

                // Redirect based on user role (admin or staff)
                String role = user.getRole();

                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("adminDashboard.jsp");
                } else if ("staff".equalsIgnoreCase(role)) {
                    response.sendRedirect("staffDashboard.jsp");
                } else {
                    // Unknown role, redirect to generic success or error page
                    log.warn("Unknown role '{}' for user '{}'", role, username);
                    response.sendRedirect("success.jsp");
                }

            } else {
                log.warn("Login failed or inactive user for username '{}'", username);
                // Forward to login page with error message instead of redirect
                request.setAttribute("errorMessage", "Invalid username or password, or account inactive.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (ServiceException e) {
            log.error("Login error for username '{}'", username, e);
            // Forward to error page or login page with error message
            request.setAttribute("errorMessage", "An internal error occurred. Please try again later.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
