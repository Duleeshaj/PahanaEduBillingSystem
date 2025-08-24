package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.User;
import org.junit.jupiter.api.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new FakeUserDAO();

        // Seed users (store hashed passwords)
        dao.addUser(new User(0, "alice", hash("secret"), "ADMIN", true));
        dao.addUser(new User(0, "bob",   hash("p@ss"),   "STAFF", true));
        dao.addUser(new User(0, "carol", hash("locked"), "STAFF", false));
    }

    @Test
    @DisplayName("Login succeeds for active user with correct password")
    void login_success_active() throws Exception {
        User u = dao.getUserByUsernameAndPassword("alice", "secret");
        assertNotNull(u);
        assertEquals("alice", u.getUsername());
        assertEquals("ADMIN", u.getRole());
        assertTrue(u.isActive());
    }

    @Test
    @DisplayName("Login fails with wrong password")
    void login_wrongPassword() throws Exception {
        assertNull(dao.getUserByUsernameAndPassword("bob", "wrong"));
    }

    @Test
    @DisplayName("Login fails when user is inactive")
    void login_inactive() throws Exception {
        assertNull(dao.getUserByUsernameAndPassword("carol", "locked"));
    }

    @Test
    @DisplayName("Get by username returns stored (hashed) password")
    void getByUsername_returnsHashed() throws Exception {
        User u = dao.getUserByUsername("bob");
        assertNotNull(u);
        assertEquals("bob", u.getUsername());
        assertEquals(hash("p@ss"), u.getPassword(), "Password should be stored as SHA-256 hex");
    }

    @Test
    @DisplayName("Update active status toggles account state")
    void updateActiveStatus() throws Exception {
        User alice = dao.getUserByUsername("alice");
        assertTrue(alice.isActive());

        boolean ok = dao.updateUserActiveStatus(alice.getUserId(), false);
        assertTrue(ok);

        User reloaded = dao.getUserByUsername("alice");
        assertFalse(reloaded.isActive());
        assertNull(dao.getUserByUsernameAndPassword("alice", "secret"), "Inactive users should not authenticate");
    }

    @Test
    @DisplayName("Update password expects hashed and enforces new credential")
    void updatePassword_hashed() throws Exception {
        User bob = dao.getUserByUsername("bob");
        assertNotNull(bob);

        // Old password works
        assertNotNull(dao.getUserByUsernameAndPassword("bob", "p@ss"));

        // Change to new password (hash provided)
        boolean ok = dao.updateUserPassword(bob.getUserId(), hash("newpass"));
        assertTrue(ok);

        // Old no longer works, new works
        assertNull(dao.getUserByUsernameAndPassword("bob", "p@ss"));
        assertNotNull(dao.getUserByUsernameAndPassword("bob", "newpass"));
    }

    @Test
    @DisplayName("List by role returns all users with that role")
    void listByRole_filters() throws Exception {
        List<User> staff = dao.listByRole("STAFF");
        assertEquals(2, staff.size());
        assertEquals("bob", staff.get(0).getUsername());
        assertEquals("carol", staff.get(1).getUsername());
    }

    @Test
    @DisplayName("Audit logs are recorded for ops")
    void auditLogs_recorded() throws Exception {
        // Trigger a couple of actions
        dao.getUserByUsernameAndPassword("alice", "secret");
        dao.getUserByUsernameAndPassword("bob", "wrong");

        List<String> logs = dao.getAuditLogs();
        // at least the seeded adds + 2 auth attempts
        assertTrue(logs.size() >= 5);
        assertTrue(logs.stream().anyMatch(s -> s.contains("ADD alice")));
        assertTrue(logs.stream().anyMatch(s -> s.contains("ADD bob")));
        assertTrue(logs.stream().anyMatch(s -> s.contains("ADD carol")));
        assertTrue(logs.stream().anyMatch(s -> s.contains("AUTH alice SUCCESS")));
        assertTrue(logs.stream().anyMatch(s -> s.contains("AUTH bob FAIL")));
    }

    @Test
    @DisplayName("Adding duplicate username throws DaoException")
    void addUser_duplicateUsername() {
        DaoException ex = assertThrows(DaoException.class, () ->
                dao.addUser(new User(0, "alice", hash("x"), "ADMIN", true))
        );
        assertTrue(ex.getMessage().toLowerCase().contains("exists"));
    }

    // --- helper (kept here to avoid changing production code) ---
    private static String hash(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] d = md.digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(d.length * 2);
        for (byte b : d) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
