package com.pahana.edu;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserManagementTestApp {
    private static final Logger log = LoggerFactory.getLogger(UserManagementTestApp.class);

    public static void main(String[] args) {
        UserService userService = new UserService();

        try (Scanner scanner = new Scanner(System.in)) {

            // Admin adding Staff
            System.out.println("Admin adding a Staff user:");
            User staffUser = new User();
            System.out.print("Enter Staff username: ");
            staffUser.setUsername(scanner.nextLine());
            System.out.print("Enter Staff password: ");
            staffUser.setPassword(scanner.nextLine());
            staffUser.setRole("STAFF");
            staffUser.setActive(true);

            boolean staffAdded = userService.addUser(staffUser, "ADMIN");
            if (staffAdded) {
                log.info("✅ Staff user '{}' added successfully by Admin.", staffUser.getUsername());
                User staffFromDb = userService.authenticateUser(staffUser.getUsername(), staffUser.getPassword());
                if (staffFromDb != null) {
                    staffUser.setUserId(staffFromDb.getUserId());
                }
            } else {
                log.warn("⚠ Failed to add Staff user '{}'.", staffUser.getUsername());
            }

            // Admin adding Customer
            System.out.println("\nAdmin adding a Customer user:");
            User customerUserByAdmin = new User();
            System.out.print("Enter Customer username: ");
            customerUserByAdmin.setUsername(scanner.nextLine());
            // No password for customer
            customerUserByAdmin.setPassword(null);
            customerUserByAdmin.setRole("CUSTOMER");
            customerUserByAdmin.setActive(true);

            boolean customerAddedByAdmin = userService.addUser(customerUserByAdmin, "ADMIN");
            if (customerAddedByAdmin) {
                log.info("✅ Customer user '{}' added successfully by Admin.", customerUserByAdmin.getUsername());
                User custFromDb = userService.getUserByUsername(customerUserByAdmin.getUsername());
                if (custFromDb != null) {
                    customerUserByAdmin.setUserId(custFromDb.getUserId());
                }
            } else {
                log.warn("⚠ Failed to add Customer user '{}'.", customerUserByAdmin.getUsername());
            }

            // Staff adding Customer
            System.out.println("\nStaff adding a Customer user:");
            User customerUserByStaff = new User();
            System.out.print("Enter Customer username: ");
            customerUserByStaff.setUsername(scanner.nextLine());
            customerUserByStaff.setPassword(null);
            customerUserByStaff.setRole("CUSTOMER");
            customerUserByStaff.setActive(true);

            boolean customerAddedByStaff = userService.addUser(customerUserByStaff, "STAFF");
            if (customerAddedByStaff) {
                log.info("✅ Customer user '{}' added successfully by Staff.", customerUserByStaff.getUsername());
                User custFromDb = userService.getUserByUsername(customerUserByStaff.getUsername());
                if (custFromDb != null) {
                    customerUserByStaff.setUserId(custFromDb.getUserId());
                }
            } else {
                log.warn("⚠ Failed to add Customer user '{}'.", customerUserByStaff.getUsername());
            }

            // Admin disables a Customer added by Staff
            if (customerUserByStaff.getUserId() != 0) {
                boolean disabledCust = userService.setUserActiveStatus(customerUserByStaff.getUserId(), false, "ADMIN");
                if (disabledCust) {
                    log.info("✅ Customer '{}' disabled by Admin.", customerUserByStaff.getUsername());
                } else {
                    log.warn("⚠ Failed to disable Customer '{}'.", customerUserByStaff.getUsername());
                }
            }

            // Admin disables a Staff user
            if (staffUser.getUserId() != 0) {
                boolean disabledStaff = userService.setUserActiveStatus(staffUser.getUserId(), false, "ADMIN");
                if (disabledStaff) {
                    log.info("✅ Staff '{}' disabled by Admin.", staffUser.getUsername());
                } else {
                    log.warn("⚠ Failed to disable Staff '{}'.", staffUser.getUsername());
                }
            }

            // Admin resets Staff password
            if (staffUser.getUserId() != 0) {
                boolean resetPwd = userService.resetPassword(staffUser.getUserId(), "newStrongPassword123", "ADMIN");
                if (resetPwd) {
                    log.info("✅ Staff '{}' password reset by Admin.", staffUser.getUsername());
                } else {
                    log.warn("⚠ Failed to reset password for Staff '{}'.", staffUser.getUsername());
                }
            }

            // Admin views audit logs
            log.info("\nAdmin viewing audit logs:");
            userService.viewAuditLogs("ADMIN");

        } catch (ServiceException e) {
            log.error("❌ ServiceException: {}", e.getMessage(), e);
        }
    }
}
