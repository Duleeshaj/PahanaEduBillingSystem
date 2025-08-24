package com.pahana.edu.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("Default constructor should set Java defaults")
    void defaultConstructor_defaults() {
        Customer c = new Customer();
        assertAll(
                () -> assertEquals(0, c.getAccountNumber()),
                () -> assertNull(c.getName()),
                () -> assertNull(c.getAddress()),
                () -> assertNull(c.getTelephone()),
                () -> assertNull(c.getEmail())
        );
    }

    @Test
    @DisplayName("Parameterized constructor sets all fields")
    void parameterizedConstructor_setsAll() {
        Customer c = new Customer(1001, "Alice", "123 Main St", "0771234567", "alice@example.com");
        assertAll(
                () -> assertEquals(1001, c.getAccountNumber()),
                () -> assertEquals("Alice", c.getName()),
                () -> assertEquals("123 Main St", c.getAddress()),
                () -> assertEquals("0771234567", c.getTelephone()),
                () -> assertEquals("alice@example.com", c.getEmail())
        );
    }

    @Test
    @DisplayName("Getters/Setters should update values")
    void gettersSetters_work() {
        Customer c = new Customer();
        c.setAccountNumber(2002);
        c.setName("Bob");
        c.setAddress("456 Beach Rd");
        c.setTelephone("0719876543");
        c.setEmail("bob@example.com");

        assertAll(
                () -> assertEquals(2002, c.getAccountNumber()),
                () -> assertEquals("Bob", c.getName()),
                () -> assertEquals("456 Beach Rd", c.getAddress()),
                () -> assertEquals("0719876543", c.getTelephone()),
                () -> assertEquals("bob@example.com", c.getEmail())
        );
    }

    @Test
    @DisplayName("Fields are mutable")
    void fields_areMutable() {
        Customer c = new Customer(1, "X", "A", "T", "E");
        c.setName("Y");
        c.setAddress("B");
        c.setTelephone("U");
        c.setEmail("F");
        c.setAccountNumber(2);

        assertAll(
                () -> assertEquals(2, c.getAccountNumber()),
                () -> assertEquals("Y", c.getName()),
                () -> assertEquals("B", c.getAddress()),
                () -> assertEquals("U", c.getTelephone()),
                () -> assertEquals("F", c.getEmail())
        );
    }
}
