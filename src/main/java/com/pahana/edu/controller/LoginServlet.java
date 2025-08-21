package com.pahana.edu.controller;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // If already logged in, send to the relevant dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Object roleObj = session.getAttribute("role");
            String role = roleObj != null ? roleObj.toString() : "";
            if ("ADMIN".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/admin-dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/staff-dashboard.jsp");
            }
            return;
        }
        // Show login page
        forwardToLogin(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String username = trimOrNull(request.getParameter("username"));
        final String password = trimOrNull(request.getParameter("password"));

        if (isBlank(username) || isBlank(password)) {
            request.setAttribute("error", "Username and password are required.");
            forwardToLogin(request, response);
            return;
        }

        try {
            // Service will throw ServiceException with a user-friendly message if anything is wrong
            User user = userService.authenticateUser(username, password);

            // Success: set session and route by role
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole()); // "ADMIN" or "STAFF"

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin-dashboard.jsp");
            } else if ("STAFF".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/staff-dashboard.jsp");
            } else {
                log.warning("Unknown role '" + user.getRole() + "' for user '" + safeLog(username) + "'");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        } catch (ServiceException e) {
            // Expected auth failures (invalid creds / inactive / etc.)
            log.log(Level.INFO, "Login failed for username '" + safeLog(username) + "': " + e.getMessage());
            request.setAttribute("error", e.getMessage());
            forwardToLogin(request, response);
        } catch (Exception e) {
            // Unexpected issue
            log.log(Level.SEVERE, "Login error for username '" + safeLog(username) + "'", e);
            request.setAttribute("error", "An internal error occurred. Please try again later.");
            forwardToLogin(request, response);
        }
    }

    private static String trimOrNull(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String safeLog(String s) {
        // Avoid logging nulls or raw user input with control chars
        return s == null ? "<null>" : s.replaceAll("[\\r\\n\\t]", " ");
    }

    /** Forward safely to /login.jsp; falls back to error.jsp if forwarding fails. */
    private static void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (ServletException se) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
