package com.pahana.edu.util;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class PasswordUtils {

    // Hash a plain text password using SHA-256
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Verify a plain password against a hashed password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) throws Exception {
        String hashOfInput = hashPassword(plainPassword);
        return hashOfInput.equals(hashedPassword);
    }
}
