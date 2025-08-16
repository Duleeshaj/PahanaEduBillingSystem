package com.pahana.edu;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class UserManagementTestApp {

    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(UserManagementTestApp.class);

    public static void main(String[] args) {

        System.out.println("=== User Management Test App ===");

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add User");
            System.out.println("2. Enable/Disable User");
            System.out.println("3. Reset User Password");
            System.out.println("4. View Audit Logs");
            System.out.println("5. Exit");
            System.out.print("Option: ");
            int option = Integer.parseInt(scanner.nextLine());

            try {
                switch (option) {
                    case 1 -> addUserTest();
                    case 2 -> toggleUserStatusTest();
                    case 3 -> resetPasswordTest();
                    case 4 -> viewAuditLogsTest();
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option!");
                }
            } catch (ServiceException e) {
                log.error("Service Exception: {}", e.getMessage(), e);
                System.out.println("Service Error: " + e.getMessage());
            } catch (Exception e) {
                log.error("Unexpected Error: {}", e.getMessage(), e);
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        }
    }

    // ----------------- Test Methods -----------------

    private static void addUserTest() throws ServiceException {
        System.out.println("\n--- Add User ---");
        System.out.print("Enter your role (Admin/Staff): ");
        String addedByRole = scanner.nextLine().trim();

        System.out.print("Enter new user's username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password (leave blank for customer without password): ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter role of new user (Staff/Customer): ");
        String role = scanner.nextLine().trim();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password.isEmpty() ? null : password);
        newUser.setRole(role);

        boolean success = userService.addUser(newUser, addedByRole);
        System.out.println(success ? "User added successfully!" : "Failed to add user.");
    }

    private static void toggleUserStatusTest() throws ServiceException {
        System.out.println("\n--- Enable/Disable User ---");
        System.out.print("Enter Admin role to proceed: ");
        String performedByRole = scanner.nextLine().trim();

        System.out.print("Enter user ID to update: ");
        int userId = Integer.parseInt(scanner.nextLine());

        System.out.print("Set Active? (true/false): ");
        boolean isActive = Boolean.parseBoolean(scanner.nextLine());

        boolean success = userService.setUserActiveStatus(userId, isActive, performedByRole);
        System.out.println(success ? "User status updated successfully!" : "Failed to update user status.");
    }

    private static void resetPasswordTest() throws ServiceException {
        System.out.println("\n--- Reset Password ---");
        System.out.print("Enter Admin role to proceed: ");
        String performedByRole = scanner.nextLine().trim();

        System.out.print("Enter user ID to reset password: ");
        int userId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();

        boolean success = userService.resetPassword(userId, newPassword, performedByRole);
        System.out.println(success ? "Password reset successfully!" : "Failed to reset password.");
    }

    private static void viewAuditLogsTest() throws ServiceException {
        System.out.println("\n--- Audit Logs ---");
        System.out.print("Enter Admin role to proceed: ");
        String performedByRole = scanner.nextLine().trim();

        List<String> logs = userService.viewAuditLogs(performedByRole);
        if (logs.isEmpty()) {
            System.out.println("No audit logs found.");
        } else {
            System.out.println("Audit Logs:");
            logs.forEach(System.out::println);
        }
    }
}
