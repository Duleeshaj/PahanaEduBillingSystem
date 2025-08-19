package com.pahana.edu.controller;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(LoginServlet.class.getName());
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String username = trimOrNull(request.getParameter("username"));
        final String password = trimOrNull(request.getParameter("password"));

        try {
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                request.setAttribute("errorMessage", "Username and password are required.");
                forwardToLogin(request, response);
                return;
            }

            User user = userService.authenticateUser(username, password);

            if (user != null && user.isActive()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("loggedUser", user);

                String role = user.getRole();
                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("adminDashboard.jsp");
                } else if ("staff".equalsIgnoreCase(role)) {
                    response.sendRedirect("staffDashboard.jsp");
                } else {
                    log.warning("Unknown role '" + role + "' for user '" + username + "'");
                    response.sendRedirect("success.jsp");
                }
            } else {
                log.info("Login failed or inactive user for username '" + username + "'");
                request.setAttribute("errorMessage", "Invalid username or password, or account inactive.");
                forwardToLogin(request, response);
            }

        } catch (ServiceException e) {
            log.log(Level.SEVERE, "Login error for username '" + username + "'", e);
            request.setAttribute("errorMessage", "An internal error occurred. Please try again later.");
            forwardToLogin(request, response);
        }
    }

    private static String trimOrNull(String s) {
        return s == null ? null : s.trim();
    }

    /**Forward safely to login.jsp. Falls back to error.jsp if forwarding fails.*/
    private static void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception se) {
            response.sendRedirect("error.jsp");
        }
    }
}
