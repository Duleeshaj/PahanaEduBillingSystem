package com.pahana.edu.controller;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String addedByRole = (String) request.getSession().getAttribute("role");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        try {
            boolean success = userService.addUser(user, addedByRole);
            if (success) {
                request.setAttribute("message", "User added successfully!");
            } else {
                request.setAttribute("message", "Failed to add user!");
            }
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("userManagement.jsp").forward(request, response);
    }
}
