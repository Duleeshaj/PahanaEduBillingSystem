package com.pahana.edu.controller;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

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
            throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.authenticateUser(username, password);
            if (user != null && user.isActive()) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                response.sendRedirect("success.jsp");
            } else {
                log.warn("Login failed for username '{}'", username);
                response.sendRedirect("error.jsp");
            }
        } catch (ServiceException e) {
            log.error("Login error for username '{}'", username, e);
            response.sendRedirect("error.jsp");
        }
    }
}
