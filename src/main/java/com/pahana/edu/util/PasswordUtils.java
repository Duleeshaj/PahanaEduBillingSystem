package com.pahana.edu.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtils {

    private PasswordUtils() {}

    /** Hash with SHA-256 and return HEX UPPERCASE string (no salt, per assignment constraints). */
    public static String hashPassword(String plain) {
        if (plain == null) throw new IllegalArgumentException("Password cannot be null");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(plain.getBytes(StandardCharsets.UTF_8));
            return toHexUpper(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    /** Verify by hashing input and constant-time comparing with stored hash (HEX, case-insensitive safe). */
    public static boolean verifyPassword(String plain, String storedHash) {
        if (plain == null || storedHash == null) return false;
        String computed = hashPassword(plain);
        // Normalize to uppercase for both sides
        return constantTimeEquals(computed.toUpperCase(), storedHash.toUpperCase());
    }

    private static String toHexUpper(byte[] bytes) {
        char[] hex = new char[bytes.length * 2];
        final char[] digits = "0123456789ABCDEF".toCharArray();
        for (int i = 0, j = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hex[j++] = digits[v >>> 4];
            hex[j++] = digits[v & 0x0F];
        }
        return new String(hex);
    }

    /** Constant-time comparison to avoid timing attacks (simple). */
    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
