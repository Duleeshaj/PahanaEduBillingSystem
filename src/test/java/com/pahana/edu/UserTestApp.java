package com.pahana.edu;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserTestApp {
    private static final Logger log = LoggerFactory.getLogger(UserTestApp.class);

    public static void main(String[] args) {
        UserService userService = new UserService();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine();

            try {
                User user = userService.authenticateUser(inputUsername, inputPassword);

                if (user != null && user.isActive()) {
                    System.out.println("✅ Login successful!");
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Role: " + user.getRole());
                } else {
                    System.out.println("❌ Login failed: Invalid credentials or inactive user.");
                }

            } catch (ServiceException e) {
                log.error("❌ Login exception occurred", e);
            }
        }
    }
}
