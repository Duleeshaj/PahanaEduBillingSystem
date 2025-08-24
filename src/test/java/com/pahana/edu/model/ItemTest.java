package com.pahana.edu.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private static void assertBigDecimalEq(BigDecimal a, BigDecimal b) {
        assertTrue(a == b || (a != null && b != null && a.compareTo(b) == 0),
                () -> "Expected " + a + " to equal " + b + " (by value)");
    }

    @Test
    @DisplayName("Default constructor should set Java defaults")
    void defaultConstructor_defaults() {
        Item i = new Item();
        assertAll(
                () -> assertEquals(0, i.getItemId()),
                () -> assertNull(i.getName()),
                () -> assertNull(i.getDescription()),
                () -> assertNull(i.getPrice()),
                () -> assertEquals(0, i.getStock())
        );
    }

    @Test
    @DisplayName("Full constructor sets all fields")
    void fullConstructor_setsAll() {
        Item i = new Item(10, "Pen", "Blue ink", new BigDecimal("120.00"), 50);
        assertAll(
                () -> assertEquals(10, i.getItemId()),
                () -> assertEquals("Pen", i.getName()),
                () -> assertEquals("Blue ink", i.getDescription()),
                () -> assertBigDecimalEq(new BigDecimal("120.00"), i.getPrice()),
                () -> assertEquals(50, i.getStock())
        );
    }

    @Test
    @DisplayName("Overloaded constructor defaults itemId to 0")
    void overloadedConstructor_defaultsIdToZero() {
        Item i = new Item("Book", "A5 ruled", new BigDecimal("350.0"), 200);
        assertAll(
                () -> assertEquals(0, i.getItemId()),
                () -> assertEquals("Book", i.getName()),
                () -> assertEquals("A5 ruled", i.getDescription()),
                () -> assertBigDecimalEq(new BigDecimal("350.00"), i.getPrice()),
                () -> assertEquals(200, i.getStock())
        );
    }

    @Test
    @DisplayName("Getters/Setters update values")
    void gettersSetters_work() {
        Item i = new Item();
        i.setItemId(7);
        i.setName("Pencil");
        i.setDescription("HB");
        i.setPrice(new BigDecimal("35"));
        i.setStock(999);

        assertAll(
                () -> assertEquals(7, i.getItemId()),
                () -> assertEquals("Pencil", i.getName()),
                () -> assertEquals("HB", i.getDescription()),
                () -> assertBigDecimalEq(new BigDecimal("35.00"), i.getPrice()),
                () -> assertEquals(999, i.getStock())
        );
    }

    @Test
    @DisplayName("Price and stock are mutable")
    void priceAndStock_mutable() {
        Item i = new Item(1, "Marker", "Black", new BigDecimal("80"), 10);
        i.setPrice(new BigDecimal("90.00"));
        i.setStock(5);
        assertBigDecimalEq(new BigDecimal("90"), i.getPrice());
        assertEquals(5, i.getStock());
    }
}
