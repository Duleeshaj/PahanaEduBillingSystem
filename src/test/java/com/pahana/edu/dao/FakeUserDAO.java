package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeUserDAO implements UserDAO {

    private final Map<String, User> byUsername = new HashMap<>();
    private final AtomicInteger idSeq = new AtomicInteger(1);
    private final List<String> audit = new ArrayList<>();

    @Override
    public boolean addUser(User user) throws DaoException {
        if (user == null || user.getUsername() == null || user.getUsername().isBlank())
            throw new DaoException("Username required");
        if (byUsername.containsKey(user.getUsername()))
            throw new DaoException("Username already exists: " + user.getUsername());

        // Assign an id if missing (simulating DB auto-increment)
        if (user.getUserId() <= 0) user.setUserId(idSeq.getAndIncrement());

        // Store a defensive copy
        byUsername.put(user.getUsername(), copy(user));
        audit.add("ADD " + user.getUsername());
        return true;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws DaoException {
        User stored = byUsername.get(username);
        if (stored == null) {
            audit.add("AUTH " + username + " NOUSER");
            return null;
        }
        if (!stored.isActive()) {
            audit.add("AUTH " + username + " INACTIVE");
            return null;
        }
        String hashed = sha256Hex(password);
        if (Objects.equals(stored.getPassword(), hashed)) {
            audit.add("AUTH " + username + " SUCCESS");
            return copy(stored);
        } else {
            audit.add("AUTH " + username + " FAIL");
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) throws DaoException {
        User stored = byUsername.get(username);
        return stored == null ? null : copy(stored);
    }

    @Override
    public boolean updateUserActiveStatus(int userId, boolean isActive) throws DaoException {
        User u = findById(userId);
        if (u == null) return false;
        u.setActive(isActive);
        byUsername.put(u.getUsername(), copy(u));
        audit.add("UPDATE_ACTIVE " + userId + " -> " + isActive);
        return true;
    }

    @Override
    public boolean updateUserPassword(int userId, String hashedPassword) throws DaoException {
        if (hashedPassword == null || hashedPassword.isBlank())
            throw new DaoException("hashedPassword required");
        User u = findById(userId);
        if (u == null) return false;
        u.setPassword(hashedPassword);
        byUsername.put(u.getUsername(), copy(u));
        audit.add("UPDATE_PASSWORD " + userId);
        return true;
    }

    @Override
    public List<String> getAuditLogs() throws DaoException {
        return new ArrayList<>(audit);
    }

    @Override
    public List<User> listByRole(String role) throws DaoException {
        List<User> out = new ArrayList<>();
        for (User u : byUsername.values()) {
            if (Objects.equals(role, u.getRole())) out.add(copy(u));
        }
        // stable ordering for tests
        out.sort(Comparator.comparing(User::getUsername));
        return out;
    }

    // --- helpers ---

    private User findById(int userId) {
        for (User u : byUsername.values()) {
            if (u.getUserId() == userId) return copy(u);
        }
        return null;
    }

    private static User copy(User u) {
        User c = new User();
        c.setUserId(u.getUserId());
        c.setUsername(u.getUsername());
        c.setPassword(u.getPassword());
        c.setRole(u.getRole());
        c.setActive(u.isActive());
        return c;
    }

    private static String sha256Hex(String s) throws DaoException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(d.length * 2);
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new DaoException("Hashing failed", e);
        }
    }
}
