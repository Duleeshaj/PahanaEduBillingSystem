package com.pahana.edu;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import java.util.Optional;

public class UserTestApp {
    public static void main(String[] args) {
        UserService service = new UserService();

        try {
            // Register new user
            User user = new User();
            user.setUsername("admin123");
            user.setPassword("adminpass");
            user.setRole("ADMIN");

            boolean registered = service.registerUser(user);
            System.out.println("User registered: " + registered);

            // Try login
            Optional<User> loggedIn = service.login("admin123", "adminpass");
            if (loggedIn.isPresent()) {
                System.out.println("✅ Logged in! Role: " + loggedIn.get().getRole());
            } else {
                System.out.println("❌ Login failed.");
            }

        } catch (ServiceException e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            e.printStackTrace(); // Optional: show full error details
        }
    }
}
