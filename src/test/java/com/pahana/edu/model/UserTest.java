package com.pahana.edu.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Default constructor should initialize with Java defaults")
    void defaultConstructor_shouldInitializeWithDefaults() {
        User u = new User();
        assertEquals(0, u.getUserId());
        assertNull(u.getUsername());
        assertNull(u.getPassword());
        assertNull(u.getRole());
        assertFalse(u.isActive());
    }

    @Test
    @DisplayName("Parameterized constructor should set all fields correctly")
    void parameterizedConstructor_shouldSetAllFields() {
        User u = new User(1, "alice", "secret", "ADMIN", true);

        assertEquals(1, u.getUserId());
        assertEquals("alice", u.getUsername());
        assertEquals("secret", u.getPassword());
        assertEquals("ADMIN", u.getRole());
        assertTrue(u.isActive());
    }

    @Test
    @DisplayName("Setters and getters should update and return values")
    void settersAndGetters_shouldUpdateAndRetrieveValues() {
        User u = new User();

        u.setUserId(42);
        u.setUsername("bob");
        u.setPassword("p@ss");
        u.setRole("USER");
        u.setActive(true);

        assertAll(
                () -> assertEquals(42, u.getUserId()),
                () -> assertEquals("bob", u.getUsername()),
                () -> assertEquals("p@ss", u.getPassword()),
                () -> assertEquals("USER", u.getRole()),
                () -> assertTrue(u.isActive())
        );
    }

    @Test
    @DisplayName("Active flag should be mutable")
    void activeFlag_shouldBeMutable() {
        User u = new User();
        assertFalse(u.isActive());
        u.setActive(true);
        assertTrue(u.isActive());
        u.setActive(false);
        assertFalse(u.isActive());
    }

    @Test
    @DisplayName("Password can be changed after construction")
    void password_canBeChanged() {
        User u = new User(5, "carol", "old", "USER", true);
        u.setPassword("new");
        assertEquals("new", u.getPassword());
    }
}
